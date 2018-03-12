package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionCircle;
import com.grepers.epicgrepers.config.ConfigProvider;
import javafx.geometry.Point2D;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Bullet representation in the world.
 */
public class Bullet extends Actor implements CollisionCircle {

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
    public Bullet(Point2D initialPos, double initialRot, String collisionGroup) {
        super(initialPos, Point2D.ZERO, initialRot);
        Map<String, Double> greperConfig = ConfigProvider.getActor("bullet");
        radius = greperConfig.get("radius");
        lifespan = greperConfig.get("lifespan");
        maxVel = greperConfig.get("maxVel");
        damage = greperConfig.get("damage");
        setVel(new Point2D(sin(initialRot), cos(initialRot)).multiply(maxVel));
        setCollisionGroup(collisionGroup);
        //setCollisionShape(new CollisionCircle(radius)); // TODO should implement or something else
    }

    /**
     * Check lifespan and update parent.
     *
     * @param elapsedMillis Time passed since last update.
     * @return Any new Actor created.
     */
    @Override
    public List<Actor> update(long elapsedMillis) {
        if (ChronoUnit.MILLIS.between(bornTime, LocalTime.now()) > lifespan * 1000d) {
            destroy();
        }
        return super.update(elapsedMillis);
    }

    @Override
    public double getRadius() {
        return 0;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }
}
