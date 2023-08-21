package com.example.exception.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessagingTemplate messagingTemplate;
    private Map<String, String> sessionDestination = new HashMap<>();
    private Map<String, Integer> roomUsers = new HashMap<>();

    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = headerAccessor.getDestination();
        sessionDestination.put(headerAccessor.getSessionId(), destination);

        if(!roomUsers.containsKey(destination)) {
            roomUsers.put(destination, 1);
        } else {
            roomUsers.put(destination, roomUsers.get(destination) + 1);
        }

        log.info(roomUsers.toString());

        assert destination != null;
        if (destination.contains("usercount")) {
            messagingTemplate.convertAndSend(destination, roomUsers.get(destination));
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        final Object channel = headerAccessor.getHeader("channel");

        String destination = sessionDestination.get(headerAccessor.getSessionId());

        log.info("terminated user destination is : {}",destination);
        log.info("Channel : {}",channel);
//        assert destination != null;
//        log.info(roomUsers.toString());
        if(destination != null && destination.contains("usercount")) {
            messagingTemplate.convertAndSend(destination, roomUsers.get(destination) > 0 ? roomUsers.get(destination) -1 : 0);
        }


    }
}














