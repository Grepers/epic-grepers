package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionCircle;
import com.grepers.epicgrepers.collisions.CollisionCone;
import com.grepers.epicgrepers.config.ConfigProvider;
import javafx.geometry.Point2D;
import lombok.Getter;
import org.apache.commons.math3.util.MathUtils;

import java.util.Map;

/**
 * Moustache actor in the world.
 */
@Getter
public class Moustache extends Sentient {

    private double sightAperture; // radians pointing to positive X
    private double sightRadius; // meters
    private double hearingRadius; // meters
    private double radius; // meters
    private double maxVel; // meters per second
    private double health;

    /**
     * Constructor initializing values.
     *
     * @param initialPos Initial position.
     */
    public Moustache(Point2D initialPos) {
        super(initialPos, Point2D.ZERO, 0d);
        Map<String, Double> greperConfig = ConfigProvider.getActor((getClass().getSimpleName()));
        health = greperConfig.get("health");
        maxVel = greperConfig.get("maxVel");
        radius = greperConfig.get("radius");
        hearingRadius = greperConfig.get("hearingRadius");
        sightRadius = greperConfig.get("sightRadius");
        sightAperture = greperConfig.get("sightAperture");
        setCollisionGroup(getId().toString());
        setCollisionShape(new CollisionCircle(initialPos, radius));
        setCollisionHearing(new CollisionCircle(initialPos, hearingRadius));
        setCollisionSight(new CollisionCone(initialPos, sightRadius, sightAperture));
    }

    /**
     * Kill the moustache without removing it from the world.
     */
    public void kill() {
        health = 0d;
        setVelVersor(Point2D.ZERO);
    }

    /**
     * Check dead status.
     *
     * @return True if dead.
     */
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
     * Rotate this moustache.
     *
     * @param rot Rotate to angle in radians.
     */
    public void rotateTo(double rot) {
        setRot(MathUtils.normalizeAngle(rot, 0d));
    }
}
