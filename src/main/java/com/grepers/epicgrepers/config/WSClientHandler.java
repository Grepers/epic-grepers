package com.grepers.epicgrepers.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepers.epicgrepers.dto.MessageFromClient;
import com.grepers.epicgrepers.player.ClientSessionHandler;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Text websocket handler for connecting clients.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WSClientHandler extends TextWebSocketHandler {

    private final World world;
    private final ObjectMapper objectMapper;

    private Map<WebSocketSession, ClientSessionHandler> sessions = new ConcurrentHashMap<>();

    /**
     * Handle connection established with another session.
     *
     * @param webSocketSession New websocket session.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) {
        // register a new handler for the new session
        ClientSessionHandler handler = new ClientSessionHandler(world,
                // message consumer
                message -> {
                    try {
                        webSocketSession.sendMessage(
                                new TextMessage(objectMapper.writeValueAsString(message) + "\n"));
                    } catch (IOException e) {
                        log.error("Error sending message", e);
                    }
                });
        sessions.put(webSocketSession, handler);
        handler.handleOpen();
    }

    /**
     * Update clients event.
     */
    @Scheduled(fixedRate = 100)
    public void updateClientSessions() {
        sessions.values().forEach(ClientSessionHandler::handleUpdate);
    }

    /**
     * Handle text message received from socket.
     *
     * @param webSocketSession Socket receiving.
     * @param textMessage      Message received.
     */
    @Override
    public void handleTextMessage(WebSocketSession webSocketSession, TextMessage textMessage) {
        try {
            MessageFromClient messageFromClient =
                    objectMapper.readValue(textMessage.getPayload(), MessageFromClient.class);
            sessions.get(webSocketSession).handleMessage(messageFromClient);
        } catch (IOException e) {
            log.error("Error receiving message", e);
        }
    }

    /**
     * Handle connection closed.
     *
     * @param webSocketSession Socket closed.
     * @param status           Status on closed.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus status) {
        sessions.remove(webSocketSession).handleClosed();
    }
}
