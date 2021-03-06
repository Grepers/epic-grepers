package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionCircle;
import com.grepers.epicgrepers.config.ConfigProvider;
import javafx.geometry.Point2D;
import lombok.Getter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Bullet representation in the world.
 */
@Getter
public class Bullet extends Actor {

    private LocalTime bornTime = LocalTime.now();
    private double radius; // meters
    private double lifespan; // seconds
    private double maxVel; // meters per second
    private double damage; // points

    /**
     * Constructor initializing values.
     *
     * @param initialPos     Initial position.
     * @param initialRot     Initial rotation.
     * @param collisionGroup Collision group's name.
     */
    Bullet(Point2D initialPos, double initialRot, String collisionGroup) {
        super(initialPos, Point2D.ZERO, initialRot);
        Map<String, Double> greperConfig = ConfigProvider.getActor((getClass().getSimpleName()));
        radius = greperConfig.get("radius");
        lifespan = greperConfig.get("lifespan");
        maxVel = greperConfig.get("maxVel");
        damage = greperConfig.get("damage");
        setVel(new Point2D(cos(initialRot), sin(initialRot)).multiply(maxVel));
        setCollisionGroup(collisionGroup);
        setCollisionShape(new CollisionCircle(initialPos, radius));
    }

    /**
     * Check lifespan and update parent.
     *
     * @param elapsedMillis Time passed since last update.
     * @return Any new Actor created.
     */
    @Override
    public List<Actor> update(long elapsedMillis) {
        if (ChronoUnit.MILLIS.between(bornTime, LocalTime.now()) > lifespan * 1000d)
            destroy();
        return super.update(elapsedMillis);
    }

    /**
     * Behavior when colliding with an {@link Actor}.
     *
     * @param actor Actor colliding with.
     */
    @Override
    public void collidingWith(Actor actor) {
        damageBy(actor.getHealth());
    }
}
