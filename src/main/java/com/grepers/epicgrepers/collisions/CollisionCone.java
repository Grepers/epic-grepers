package com.grepers.epicgrepers.collisions;

import javafx.geometry.Point2D;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Collision shape defining a cone area.
 */
@AllArgsConstructor
@Getter
public class CollisionCone implements CollisionShape {

    private final double radius; // meters
    private final double aperture; // radians pointing to positive X axis
    private Point2D center;

    /**
     * Constructor initializing values.
     *
     * @param initialPos Initial position.
     * @param radius     Circle's radius.
     */
    public CollisionCone(Point2D initialPos, double radius, double aperture) {
        this.radius = radius;
        this.aperture = aperture;
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
