package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionRectangle;
import javafx.geometry.Point2D;
import lombok.Getter;

/**
 * Wall representation in the world.
 */
@Getter
public class Wall extends Actor {

    private double width; // meters
    private double depth; // meters

    /**
     * Constructor initializing values.
     *
     * @param initialPos Initial position.
     * @param width      Wall width in X.
     * @param depth      Wall depth in Y.
     */
    Wall(Point2D initialPos, double width, double depth) {
        super(initialPos, Point2D.ZERO, 0d);
        this.width = width;
        this.depth = depth;
        setCollisionGroup(getId().toString());
        setCollisionShape(new CollisionRectangle(initialPos, width, depth));
    }

    /**
     * This method should be implemented in order to add behavior when colliding with a {@link Bullet}.
     *
     * @param bullet Bullet actor.
     */
    @Override
    public void collidingWith(Bullet bullet) {
        damageBy(bullet.getDamage());
    }
}
