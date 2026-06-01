package com.pbl.IDE.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Container {
    String containerId;
    Map<String, TerminalSession> terminalSessions;
    public Container(String containerId) {
        this.containerId = containerId;
        this.terminalSessions = new HashMap<>();
    }
}
