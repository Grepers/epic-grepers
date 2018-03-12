package com.grepers.epicgrepers.collisions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Collision shape defining a rectangular area.
 */
@RequiredArgsConstructor
@Getter
public class CollisionRectangle implements CollisionShape {
    private final double minX;
    private final double minY;
    private final double maxX;
    private final double maxY;
}
