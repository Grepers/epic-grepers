package com.grepers.epicgrepers.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WSConfig implements WebSocketConfigurer {

    private final WSHandler wsHandler;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(wsHandler, "/client")
                .setAllowedOrigins("*");
    }

}
