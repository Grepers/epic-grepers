package com.grepers.epicgrepers.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Websocket Spring configuration.
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WSConfig implements WebSocketConfigurer {

    private final WSClientHandler wsClientHandler;

    /**
     * Register handlers per websocket.
     *
     * @param registry Registry from bean.
     */
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(wsClientHandler, "/client")
                .setAllowedOrigins("*");
    }
}
