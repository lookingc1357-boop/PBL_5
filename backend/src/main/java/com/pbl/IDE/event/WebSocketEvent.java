package com.pbl.IDE.event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.pbl.IDE.service.ContainerService;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;


@Component
public class WebSocketEvent {
    
    @Autowired
    private ContainerService containerService;

    @EventListener
    public void handleSessionConnect(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        containerService.start(Objects.requireNonNull(accessor.getUser()).getName());
    }
    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        containerService.stop(Objects.requireNonNull(accessor.getUser()).getName());
    }
}
