package com.ql;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class TerminalHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String command = message.getPayload().trim();
        String output = command.isBlank()
            ? "$ "
            : "$ " + command + "\nmock terminal: command accepted by sandbox policy\n";
        session.sendMessage(new TextMessage(output));
    }
}
