package com.grepers.epicgrepers.collisions;

/**
 * Collision shape defining a rectangular area.
 */
public interface CollisionRectangle extends CollisionShape {
    double getTop();
    double getRight();
    double getBottom();
    double getLeft();
}
