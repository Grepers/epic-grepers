package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionCircle;
import javafx.geometry.Point2D;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Bullet representation in the world.
 */
public class Bullet extends Actor {

    private LocalTime bornTime = LocalTime.now();

    @Value("${bullet.radius}")
    private Double radius; // meters

    @Value("${bullet.lifespan}")
    private Long lifespan; // milliseconds

    @Value("${bullet.maxVel}")
    private Double maxVel; // meters per second

    @Value("${bullet.damage}")
    private Double damage; // points

    /**
     * Constructor initializing values.
     *
     * @param initialPos     Initial position.
     * @param initialRot     Initial rotation.
     * @param collisionGroup Collision group's name.
     */
    public Bullet(Point2D initialPos, Double initialRot, String collisionGroup) {
        super(initialPos, Point2D.ZERO, initialRot, collisionGroup);
        setVel(new Point2D(sin(initialRot), cos(initialRot)).multiply(maxVel));
        setCollisionShape(new CollisionCircle(radius));
    }

    /**
     * Check lifespan and update parent.
     *
     * @param elapsedMillis Time passed since last update.
     * @return Any new Actor created.
     */
    @Override
    public List<Actor> update(long elapsedMillis) {
        if (ChronoUnit.MILLIS.between(bornTime, LocalTime.now()) > lifespan) {
            destroy();
        }
        return super.update(elapsedMillis);
    }
}
