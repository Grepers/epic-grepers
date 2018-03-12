package com.grepers.epicgrepers.player;

import com.grepers.epicgrepers.dto.EventType;
import com.grepers.epicgrepers.dto.MessageFromClient;
import com.grepers.epicgrepers.dto.MessageToClient;
import com.grepers.epicgrepers.world.Greper;
import com.grepers.epicgrepers.world.World;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.function.Consumer;

/**
 * Session handler for a specific connected client.
 */
@Slf4j
@RequiredArgsConstructor
public class ClientSessionHandler {

    private final World world;
    private final Consumer<MessageToClient> messageSender;

    private Greper greper = null;

    /**
     * Handle connection opened.
     */
    public void handleOpen() {
        // create the new player
        greper = world.spawnGreper();
        // send initial information to client
        messageSender.accept(new MessageToClient(EventType.STATUS, Collections.singletonList(greper)));
    }

    /**
     * Process update event sending update information to client.
     */
    public void handleUpdate() {
        //TODO keep track to avoid flooding the client, just sent what has changed since last update.
        messageSender.accept(new MessageToClient(EventType.UPDATED, world.getActors()));
    }

    /**
     * Process message received from client.
     *
     * @param messageFromClient Message.
     */
    public void handleMessage(MessageFromClient messageFromClient) {
        greper.setVelVersor(messageFromClient.getVel());
        greper.rotateTo(messageFromClient.getRot());
        greper.setFiring(messageFromClient.isFiring());
    }

    /**
     * Handle connection closed.
     */
    public void handleClosed() {
        if (greper != null) {
            greper.kill();
        }
    }
}
