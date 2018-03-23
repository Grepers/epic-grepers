package com.grepers.epicgrepers.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grepers.epicgrepers.collisions.CollisionShape;
import javafx.geometry.Point2D;
import lombok.AccessLevel;
import lombok.Setter;

/**
 * Sentient actor in the world.
 */
public class Sentient extends Actor {
    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    private CollisionShape collisionHearing;
    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    private CollisionShape collisionSight;

    /**
     * Constructor initializing parameters.
     *
     * @param pos Initial position.
     * @param vel Initial velocity.
     * @param rot Initial rotation.
     */
    public Sentient(Point2D pos, Point2D vel, double rot) {
        super(pos, vel, rot);
    }
}
