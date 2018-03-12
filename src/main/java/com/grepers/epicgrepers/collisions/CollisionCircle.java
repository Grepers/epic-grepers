package com.grepers.epicgrepers.collisions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Collision shape defining a circular area.
 */
@RequiredArgsConstructor
@Getter
public class CollisionCircle implements CollisionShape {
    private final double radius;
}
