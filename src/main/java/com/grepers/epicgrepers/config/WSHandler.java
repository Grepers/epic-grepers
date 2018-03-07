package com.grepers.epicgrepers.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepers.epicgrepers.player.SessionHandler;
import com.grepers.epicgrepers.world.World;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class WSHandler extends TextWebSocketHandler {

    private final World world;
    private final ObjectMapper objectMapper;

    private Map<WebSocketSession, SessionHandler> sessions = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        SessionHandler handler = new SessionHandler(world, webSocketSession.getId(), message -> {
            try {
                webSocketSession.sendMessage(new TextMessage(message + "\n"));
            } catch (IOException e) {
                log.error("Error sending message", e);
            }
        }, objectMapper);
        sessions.put(webSocketSession, handler);
        handler.handleOpen();
    }

    @Scheduled(fixedRate = 100)
    public void updateSessions() {
        sessions.values().forEach(SessionHandler::handleUpdate);
    }

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage textMessage) {
        sessions.get(webSocketSession).handleMessage(textMessage.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) {
        sessions.remove(webSocketSession).handleClosed();
    }
}
