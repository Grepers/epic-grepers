package com.grepers.epicgrepers.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.grepers.epicgrepers.collisions.CollisionShape;
import javafx.geometry.Point2D;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Base class for every object in the world.
 */
@Getter
@RequiredArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type") // class name in json
public class Actor<S extends CollisionShape> {

    private UUID id = UUID.randomUUID(); // unique id

    @NonNull
    private Point2D pos; // meters

    @NonNull
    @Setter(AccessLevel.PROTECTED)
    private Point2D vel; // meters per seconds

    @NonNull
    @Setter(AccessLevel.PROTECTED)
    private double rot; // radians

    @JsonIgnore
    private boolean destroyed = false; // ready to be cleaned up

    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    private String collisionGroup;

    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    private S collisionShape;

    /**
     * Update this Actor and return any generated new Actors.
     *
     * @param elapsedMillis Time passed since last update.
     * @return List of new Actors created, or empty list.
     */
    public List<Actor> update(long elapsedMillis) {
        // update position if needed
        if (!vel.equals(Point2D.ZERO)) {
            pos = pos.add(vel.multiply(elapsedMillis / 1000d));
            collisionShape.update(pos);
        }
        return Collections.emptyList();
    }

    /**
     * Mark this Actor for clean up.
     */
    public void destroy() {
        destroyed = true;
    }
}
