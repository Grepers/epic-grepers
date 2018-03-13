package com.grepers.epicgrepers.collisions;

import javafx.geometry.Point2D;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Collision shape defining a circular area.
 */
@AllArgsConstructor
@Getter
public class CollisionCircle implements CollisionShape {

    private final double radius;
    private Point2D center;

    /**
     * Constructor initializing values.
     *
     * @param initialPos Initial position.
     * @param radius     Circle's radius.
     */
    public CollisionCircle(Point2D initialPos, double radius) {
        this.radius = radius;
        update(initialPos);
    }

    /**
     * Update the circle absolute position.
     *
     * @param newPos Updated position.
     */
    @Override
    public void update(Point2D newPos) {
        center = newPos;
    }
}
