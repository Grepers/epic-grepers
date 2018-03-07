package com.grepers.epicgrepers.dto;

import com.grepers.epicgrepers.world.Actor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessageToClient {
    private EventType eventType;
    private List<Actor> actors = new ArrayList<>();
}
