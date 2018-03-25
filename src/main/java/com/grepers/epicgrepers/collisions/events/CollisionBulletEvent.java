package com.grepers.epicgrepers.collisions.events;

import com.grepers.epicgrepers.world.Bullet;

public interface CollisionBulletEvent extends CollisionEvent {

    /**
     * This method should be implemented in order to add behavior when colliding with a {@link Bullet}
     *
     * @param bullet
     */
    default void collidingWith(Bullet bullet) {
    }
}
