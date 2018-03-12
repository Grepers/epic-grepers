package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionCircle;
import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.util.MathUtils;
import org.kohsuke.randname.RandomNameGenerator;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Greper actor in the world.
 */
@Getter
@Configurable
public class Greper extends Actor {

    private String name;

    @Value("${greper.radius}")
    private double radius; // meters

    @Value("${greper.maxVel}")
    private double maxVel; // meters per second

    @Value("${greper.health}")
    private double health;

    @Value("${greper.firerate}")
    private double firerate; // rounds per second
    private LocalTime lastFiredTime = LocalTime.now();
    @Setter
    private boolean firing = false;


    /**
     * Constructor initializing values.
     *
     * @param initialPos Initial position.
     */
    public Greper(Point2D initialPos) {
        super(initialPos, Point2D.ZERO, 0d);
        name = new RandomNameGenerator().next();
        setCollisionGroup(getId().toString());
        setCollisionShape(new CollisionCircle(radius));
    }

    /**
     * Update this greper's status.
     *
     * @param elapsedMillis Time passed since last update.
     * @return New actors created by the greper's update.
     */
    @Override
    public List<Actor> update(long elapsedMillis) {
        List<Actor> newActors = super.update(elapsedMillis);
        if (firing && ChronoUnit.MILLIS.between(lastFiredTime, LocalTime.now()) > 1000d / firerate) {
            newActors.add(new Bullet(getPos(), getRot(), getCollisionGroup()));
            lastFiredTime = LocalTime.now();
        }
        return newActors;
    }

    /**
     * Kill the greper without removing it from the world.
     */
    public void kill() {
        health = 0d;
    }

    public boolean isDead() {
        return health == 0d;
    }

    /**
     * Set velocity versor and thus velocity vector.
     *
     * @param velVersor Velocity versor.
     */
    public void setVelVersor(Point2D velVersor) {
        setVel(velVersor.normalize().multiply(maxVel));
    }

    /**
     * Rotate this greper.
     *
     * @param rot Rotate to angle in radians.
     */
    public void rotateTo(double rot) {
        setRot(MathUtils.normalizeAngle(rot, 0d));
    }
}
