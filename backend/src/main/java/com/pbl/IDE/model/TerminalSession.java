package com.pbl.IDE.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDateTime;
import com.pty4j.PtyProcess;


@Getter
@AllArgsConstructor
public  class TerminalSession {
    private final String terminalId;
    private final String username;
    private String workDir;
    private final String containerId;
    private final LocalDateTime createdAt;

    private PtyProcess process;   
    public TerminalSession(String terminalId, String username, String workDir,
                            String containerId, LocalDateTime createdAt) {
        this.terminalId = terminalId;
        this.username = username;
        this.workDir = workDir;
        this.containerId = containerId;
        this.createdAt = createdAt;
        this.process = null;
    }

    public void setWorkDir(String workDir) {  
        this.workDir = workDir;
    }

    public void setProcess(PtyProcess process) {
        this.process = process;
    }
}