package com.grepers.epicgrepers.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.geometry.Point2D;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@Getter
public class Actor {
    private UUID id;
    @JsonIgnore
    private boolean destroyed;
    private Point2D pos; // meters
    private Point2D vel; // meters / seconds
    private Double rot; // radians

    public Actor(Point2D initialPos, Point2D initialVel, Double initialRot) {
        id = UUID.randomUUID();
        destroyed = false;
        pos = initialPos;
        vel = initialVel;
        rot = initialRot;
    }

    public List<Actor> update(long elapsedMillis) {
        List<Actor> newActors = new ArrayList<>();
        pos = pos.add(vel.multiply(elapsedMillis / 1000d));
        return newActors;
    }

    public void destroy() {
        destroyed = true;
    }
}
