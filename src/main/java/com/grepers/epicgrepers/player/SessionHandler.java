package com.grepers.epicgrepers.player;

import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class SessionHandler {

    private final String id;
    private final Consumer<String> messageSender;

    public void handleMessage(String payload) {
        messageSender.accept("id=" + id + " message=" + payload);
    }

    public void handleClosed() {

    }
}
