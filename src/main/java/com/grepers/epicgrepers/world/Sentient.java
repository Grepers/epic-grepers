package com.grepers.epicgrepers.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.grepers.epicgrepers.collisions.CollisionCircle;
import com.grepers.epicgrepers.collisions.CollisionCone;
import com.grepers.epicgrepers.collisions.CollisionShape;
import com.grepers.epicgrepers.config.ConfigProvider;
import javafx.geometry.Point2D;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Sentient actor in the world.
 */
@Getter
public abstract class Sentient extends Actor {

    private double sightAperture; // radians pointing to positive X
    private double sightRadius; // meters
    private double hearingRadius; // meters

    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    private CollisionShape collisionHearing;
    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    private CollisionShape collisionSight;

    /**
     * Constructor initializing parameters.
     *
     * @param initialPos Initial position.
     * @param vel        Initial velocity.
     * @param rot        Initial rotation.
     */
    Sentient(Point2D initialPos, Point2D vel, double rot) {
        super(initialPos, vel, rot);
        Map<String, Double> greperConfig = ConfigProvider.getActor((getClass().getSimpleName()));
        hearingRadius = greperConfig.get("hearingRadius");
        sightRadius = greperConfig.get("sightRadius");
        sightAperture = greperConfig.get("sightAperture");
        setCollisionHearing(new CollisionCircle(initialPos, hearingRadius));
        setCollisionSight(new CollisionCone(initialPos, sightRadius, sightAperture));
    }

    /**
     * Check dead status.
     *
     * @return True if dead.
     */
    public boolean isDead() {
        return getHealth() == 0d;
    }

    /**
     * Kill the actor without removing it from the world.
     */
    public void kill() {
        setHealth(0d);
        setVel(Point2D.ZERO);
    }


    /**
     * This method should be implemented in order to add behavior when colliding with a {@link Bullet}
     *
     * @param bullet
     */
    @Override
    public void collidingWith(Bullet bullet) {
        damageBy(bullet.getDamage());
    }

    /**
     * This method should be implemented in order to add behavior when colliding with a {@link Wall}
     *
     * @param wall
     */
    @Override
    public void collidingWith(Wall wall) {
        //TODO calculations to avoid walking through walls, instead walk alongside them
        setVel(Point2D.ZERO);
    }
}
