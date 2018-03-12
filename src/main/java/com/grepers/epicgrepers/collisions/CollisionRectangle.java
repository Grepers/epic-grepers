package com.grepers.epicgrepers.collisions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Collision shape defining a rectangular area.
 */
@RequiredArgsConstructor
@Getter
public class CollisionRectangle implements CollisionShape {
    private final double top;
    private final double right;
    private final double bottom;
    private final double left;
}
