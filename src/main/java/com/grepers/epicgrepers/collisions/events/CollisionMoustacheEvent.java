package com.grepers.epicgrepers.collisions.events;

import com.grepers.epicgrepers.world.Moustache;

public interface CollisionMoustacheEvent extends CollisionEvent {

    /**
     * This method should be implemented in order to add behavior when colliding with a {@link Moustache}
     *
     * @param moustache
     */
    default void collidingWith(Moustache moustache) {
    }
}
