package com.pbl.IDE.controller;

import java.util.Map;

import com.pbl.IDE.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.IDE.entity.User;
import com.pbl.IDE.dto.UserDTO;
import com.pbl.IDE.service.UserService;
import com.pbl.IDE.config.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private ContainerService containerService;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> credentials,
            HttpServletResponse response) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "username và password là bắt buộc"));
        }   
        User user = userService.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            String token = jwtUtil.generateToken(username);
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setSecure(true);
            response.setHeader("Set-Cookie",
                    "token=" + token + "; Path=/; HttpOnly; Secure; SameSite=None");
            response.addCookie(cookie);
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Đăng nhập thất bại"));
    }

    @PostMapping("/register")
    public UserDTO signup(@RequestBody User user) {
        return new UserDTO(userService.createUser(user));
    }
}
