package com.grepers.epicgrepers.dto;

import com.grepers.epicgrepers.world.Actor;
import lombok.Data;

import java.util.List;

/**
 * Message DTO to client.
 */
@Data
public class MessageToClient {
    private final EventType eventType;
    private final List<Actor> actors;
}
