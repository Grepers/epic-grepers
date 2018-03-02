package com.grepers.epicgrepers.config;

import com.grepers.epicgrepers.player.SessionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class WSHandler extends TextWebSocketHandler {

    private Map<WebSocketSession, SessionHandler> sessions = new HashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage textMessage) {
        sessions.get(webSocketSession).handleMessage(textMessage.getPayload());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        sessions.put(webSocketSession, new SessionHandler(webSocketSession.getId(), message -> {
            try {
                webSocketSession.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.error("Error sending message", e);
            }
        }));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) {
        sessions.remove(webSocketSession).handleClosed();
    }
}
