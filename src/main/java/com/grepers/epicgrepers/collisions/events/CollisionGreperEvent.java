package com.grepers.epicgrepers.collisions.events;

import com.grepers.epicgrepers.world.Greper;

public interface CollisionGreperEvent extends CollisionEvent {

    /**
     * This method should be implemented in order to add behavior when colliding with a {@link Greper}
     * @param greper
     */
    default void collidingWith(Greper greper) {}
}
