package com.grepers.epicgrepers.collisions.events;

/**
 * A collision event listener implementing all the methods in all the {@link CollisionBulletEvent},
 * {@link CollisionGreperEvent}, {@link CollisionMoustacheEvent} and {@link CollisionWallEvent} interfaces.
 * <p>
 * All collision event interfaces have their methods with an empty default body so you can give an implementation
 * only for those events you are interested in.
 */
public interface CollisionListener extends CollisionBulletEvent, CollisionGreperEvent, CollisionMoustacheEvent, CollisionWallEvent {
}
