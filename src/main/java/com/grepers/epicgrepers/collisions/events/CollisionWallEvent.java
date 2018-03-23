package com.grepers.epicgrepers.collisions.events;

import com.grepers.epicgrepers.world.Wall;

public interface CollisionWallEvent extends CollisionEvent {

    /**
     * This method should be implemented in order to add behavior when colliding with a {@link Wall}
     * @param wall
     */
    default void collidingWith(Wall wall) {}
}
