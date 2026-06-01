package com.pbl.IDE.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;
import com.pty4j.WinSize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TerminalService {

    private final ConcurrentHashMap<String, TerminalSession> terminals = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ContainerService containerService;

    // DockerClient không còn cần thiết cho phần terminal nữa
    // private final DockerClient dockerClient; ← có thể xóa nếu không dùng chỗ khác

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private static final String OUTPUT_DESTINATION = "/queue/terminal";

    // =========================
    // CREATE TERMINAL SESSION (Interactive PTY)
    // =========================
    public String createNewTerminal(String username, String workDir) {
        if(containerService.isStopped(username)) {
            containerService.start(username);
        }
        String terminalId = "ID" + UUID.randomUUID().toString().substring(0, 8);

        TerminalSession session = new TerminalSession(
                terminalId, username, workDir, LocalDateTime.now());
        terminals.put(terminalId, session);

        // Khởi tạo PTY ngay khi tạo session
        startPtySession(session);

        return terminalId;
    }

    private void startPtySession(TerminalSession session) {
        executorService.submit(() -> {
            try {
                // Command để chạy shell bên trong container
                String[] command = {
                        "docker", "exec",
                        "-it", // interactive
                        session.getUsername(),
                        "/bin/sh", "-l" // login shell (hoặc /bin/bash nếu container có)
                };

                Map<String, String> env = Map.of("TERM", "xterm-256color");

                PtyProcess process = new PtyProcessBuilder()
                        .setCommand(command)
                        .setEnvironment(env)
                        .setRedirectErrorStream(true) // gộp stderr vào stdout
                        .start();
                OutputStream os = process.getOutputStream();
                os.write(("cd " + session.getWorkDir() + "\n").getBytes());
                os.flush();

                session.setProcess(process);

                log.info("PTY started for terminal {} in container {}",
                        session.getTerminalId(), session.getUsername());

                // Thread đọc output liên tục và gửi về client qua WebSocket
                readOutputLoop(session);

            } catch (Exception e) {
                log.error("Failed to start PTY for terminal {}", session.getTerminalId(), e);
                sendError(session, "Failed to start terminal: " + e.getMessage());
            }
        });
    }

    private void readOutputLoop(TerminalSession session) {
        PtyProcess process = session.getProcess();
        try (InputStream is = process.getInputStream()) {
            byte[] buffer = new byte[8192];
            int len;

            while ((len = is.read(buffer)) != -1) {
                String output = new String(buffer, 0, len);

                // Gửi output về frontend
                messagingTemplate.convertAndSendToUser(
                        session.getUsername(),
                        OUTPUT_DESTINATION + session.terminalId,
                        Map.of(
                                "terminalId", session.getTerminalId(),
                                "output", output,
                                "type", "stdout"));
            }

            // Process kết thúc
            int exitCode = process.waitFor();
            sendExitMessage(session, exitCode);

        } catch (Exception e) {
            log.error("Error reading PTY output", e);
            sendError(session, "Terminal error: " + e.getMessage());
        } finally {
            closeSession(session.getTerminalId());
        }
    }

    // =========================
    // SEND INPUT (từ frontend → shell)
    // =========================
    public void sendInput(String terminalId, String input) {
        TerminalSession session = terminals.get(terminalId);
        if (session == null || session.getProcess() == null) {
            return;
        }

        try {
            OutputStream os = session.getProcess().getOutputStream();
            os.write(input.getBytes());
            os.flush();
        } catch (IOException e) {
            log.error("Failed to write input to PTY", e);
        }
    }

    // =========================
    // RESIZE TERMINAL (rất quan trọng cho vi, nano, htop...)
    // =========================
    public void resizeTerminal(String terminalId, int cols, int rows) {
        TerminalSession session = terminals.get(terminalId);
        if (session != null && session.getProcess() != null) {
            try {
                session.getProcess().setWinSize(new WinSize(cols, rows));
                log.debug("Resized terminal {} to {}x{}", terminalId, cols, rows);
            } catch (Exception e) {
                log.warn("Failed to resize PTY for terminal {}", terminalId, e);
            }
        }
    }

    // =========================
    // EXECUTE COMMAND (nếu vẫn muốn chạy lệnh không interactive)
    // =========================
    // Bạn có thể giữ hàm cũ hoặc bỏ nếu không cần nữa

    private void sendError(TerminalSession session, String message) {
        messagingTemplate.convertAndSendToUser(
                session.getUsername(),
                OUTPUT_DESTINATION + session.getTerminalId(),
                Map.of("terminalId", session.getTerminalId(),
                        "output", message,
                        "type", "error"));
    }

    private void sendExitMessage(TerminalSession session, int exitCode) {
        messagingTemplate.convertAndSendToUser(
                session.getUsername(),
                OUTPUT_DESTINATION + session.getTerminalId(),
                Map.of("terminalId", session.getTerminalId(),
                        "output", "\nProcess exited with code " + exitCode + "\n",
                        "type", "exit"));
    }

    public void closeSession(String terminalId) {
        TerminalSession session = terminals.remove(terminalId);
        if (session != null && session.getProcess() != null) {
            session.getProcess().destroy();
        }
    }

    // =========================
    // SESSION MODEL (cập nhật)
    // =========================
    @Getter
    @AllArgsConstructor
    public static class TerminalSession {
        private final String terminalId;
        private final String username;
        private String workDir;
        private final LocalDateTime createdAt;

        private PtyProcess process; // ← thêm field này

        public TerminalSession(String terminalId, String username, String workDir,
                LocalDateTime createdAt) {
            this.terminalId = terminalId;
            this.username = username;
            this.workDir = workDir;
            this.createdAt = createdAt;
            this.process = null;
        }

        public void setWorkDir(String workDir) { // chưa dùng
            this.workDir = workDir;
        }

        public void setProcess(PtyProcess process) {
            this.process = process;
        }
    }
}