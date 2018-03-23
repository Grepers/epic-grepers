package com.grepers.epicgrepers.collisions;

import com.grepers.epicgrepers.world.Actor;

public interface CollisionEvent<A extends Actor> {

    public void collidingWith(A actor);
}
