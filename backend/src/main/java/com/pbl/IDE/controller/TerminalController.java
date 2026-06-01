package com.pbl.IDE.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.pbl.IDE.service.TerminalService;

@Controller
public class TerminalController {
    @Autowired
    private TerminalService terminalService;

    @MessageMapping("/hello")
    @SendToUser("/queue/terminal")
    public String handle(String message) {
        return "Personal response: " + message;
    }

    @MessageMapping("/terminal/exec")
    public void executeCommand(Map<String, Object> payload) {
        String terminalId = (String) payload.get("terminalId");
        String command = (String) payload.get("command");

        if (terminalId == null || command == null) {
            throw new IllegalArgumentException("terminalId và command là bắt buộc");
        }
        terminalService.sendInput(terminalId, command);
    }

    @MessageMapping("/create-terminal")
    @SendToUser("/queue/terminal-created")
    public Map<String, String> createTerminal(Principal principal, Map<String, String> payload) {
        String username = principal.getName();
        String terminalId = terminalService.createNewTerminal(username, payload.get("workDir"));
        return Map.of(
                "terminalId", terminalId,
                "workDir", payload.get("workDir"));
    }

    // dong session terminal
    @MessageMapping("/close-terminal")
    public void closeTerminal(Map<String, String> payload) {
        String terminalId = payload.get("terminalId");
        if (terminalId != null) {
            terminalService.closeSession(terminalId);
        }
    }
}