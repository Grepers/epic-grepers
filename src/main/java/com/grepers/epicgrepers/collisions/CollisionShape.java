package com.grepers.epicgrepers.collisions;


import javafx.geometry.Point2D;

/**
 * Base interface for collision shapes.
 */
public interface CollisionShape {
    /**
     * Update this shape's absolute position.
     *
     * @param newPos Updated position.
     */
    void update(Point2D newPos);
}
