package com.grepers.epicgrepers.collisions.events;

import com.grepers.epicgrepers.world.Actor;

/**
 * A tagging interface that all collision event listener interfaces must extend.
 */
public interface CollisionEvent {

    /**
     * Behavior when colliding with an {@link Actor}.
     *
     * @param actor Actor colliding with.
     */
    default void collidingWith(Actor actor) {
    }
}
