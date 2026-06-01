package com.pbl.IDE.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pbl.IDE.entity.User;
import com.pbl.IDE.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContainerService containerService;

    public User createUser(User user) {
        containerService.createContainer(user.getUsername());
        return userRepository.save(user);
    }

    public User getUser(String usename) {
        return userRepository.findById(usename)
                .orElseThrow(() -> new RuntimeException("Not found"));
    }
}