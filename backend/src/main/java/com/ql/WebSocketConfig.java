package com.ql;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final CodeSyncHandler codeSyncHandler;
    private final TerminalHandler terminalHandler;

    public WebSocketConfig(CodeSyncHandler codeSyncHandler, TerminalHandler terminalHandler) {
        this.codeSyncHandler = codeSyncHandler;
        this.terminalHandler = terminalHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(codeSyncHandler, "/ws/code").setAllowedOrigins("*");
        registry.addHandler(terminalHandler, "/ws/terminal").setAllowedOrigins("*");
    }
}
