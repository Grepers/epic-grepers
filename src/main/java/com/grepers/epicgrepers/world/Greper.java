package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionCircle;
import com.grepers.epicgrepers.config.ConfigProvider;
import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.math3.util.MathUtils;
import org.kohsuke.randname.RandomNameGenerator;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Greper actor in the world.
 */
@Getter
public class Greper extends Sentient {

    private String name;
    private double radius; // meters
    private double maxVel; // meters per second
    private double firerate; // rounds per second
    private LocalTime lastFiredTime = LocalTime.now();
    @Setter
    private boolean firing = false;

    /**
     * Constructor initializing values.
     *
     * @param initialPos Initial position.
     */
    Greper(Point2D initialPos) {
        super(initialPos, Point2D.ZERO, 0d);
        Map<String, Double> greperConfig = ConfigProvider.getActor((getClass().getSimpleName()));
        firerate = greperConfig.get("firerate");
        maxVel = greperConfig.get("maxVel");
        radius = greperConfig.get("radius");
        name = new RandomNameGenerator().next();
        setCollisionGroup(getId().toString());
        setCollisionShape(new CollisionCircle(initialPos, radius));
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
            newActors.add(new Bullet(
                    getPos().add(new Point2D(cos(getRot()), sin(getRot())).multiply(radius)),
                    getRot(), getCollisionGroup()));
            lastFiredTime = LocalTime.now();
        }
        return newActors;
    }

    /**
     * Kill the greper without removing it from the world.
     */
    public void kill() {
        super.kill();
        setFiring(false);
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
