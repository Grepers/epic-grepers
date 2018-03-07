package com.grepers.epicgrepers.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grepers.epicgrepers.dto.EventType;
import com.grepers.epicgrepers.dto.MessageToClient;
import com.grepers.epicgrepers.world.Greper;
import com.grepers.epicgrepers.world.World;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.datetime.standard.Jsr310DateTimeFormatAnnotationFormatterFactory;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class SessionHandler {

    private final World world;
    private final String id;
    private final Consumer<String> messageSender;

    private final ObjectMapper objectMapper;
    private Greper greper = null;

    private void sendMessage(MessageToClient messageToClient) {
        try {
            messageSender.accept(objectMapper.writeValueAsString(messageToClient));
        } catch (JsonProcessingException e) {
            log.error("Cannot send message to client.", e);
        }
    }

    public void handleOpen() {
        greper = world.spawnGreper();

        MessageToClient messageToClient = new MessageToClient();
        messageToClient.setEventType(EventType.STATUS);
        messageToClient.getActors().add(greper);
        sendMessage(messageToClient);
    }

    public void handleMessage(String payload) {
    }

    public void handleClosed() {
        if (greper != null) {
            world.killGreper(greper);
        }
    }

    public void handleUpdate() {
        MessageToClient messageToClient = new MessageToClient();
        messageToClient.setEventType(EventType.UPDATED);
        messageToClient.setActors(world.getActors());
        sendMessage(messageToClient);
    }
}
