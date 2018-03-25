package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionCircle;
import com.grepers.epicgrepers.config.ConfigProvider;
import javafx.geometry.Point2D;
import lombok.Getter;

import java.util.Map;

/**
 * Moustache actor in the world.
 */
@Getter
public class Moustache extends Sentient {

    private double radius; // meters
    private double maxVel; // meters per second

    /**
     * Constructor initializing values.
     *
     * @param initialPos Initial position.
     */
    public Moustache(Point2D initialPos) {
        super(initialPos, Point2D.ZERO, 0d);
        Map<String, Double> greperConfig = ConfigProvider.getActor((getClass().getSimpleName()));
        maxVel = greperConfig.get("maxVel");
        radius = greperConfig.get("radius");
        setCollisionGroup(getId().toString());
        setCollisionShape(new CollisionCircle(initialPos, radius));
    }
}
