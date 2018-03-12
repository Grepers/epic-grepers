package com.grepers.epicgrepers.collisions;

/**
 * Collision shape defining a circular area.
 */
public interface CollisionCircle extends CollisionShape {
    double getRadius();
    double getX();
    double getY();
}
