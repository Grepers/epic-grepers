package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionRectangle;
import com.grepers.epicgrepers.collisions.CollisionShape;
import com.grepers.epicgrepers.collisions.events.CollisionActorEvent;
import com.grepers.epicgrepers.config.ConfigProvider;
import javafx.geometry.Point2D;

import java.util.Map;

/**
 * Wall representation in the world.
 */
public class Wall extends Actor implements CollisionActorEvent {

    private double width;
    private double height;
    private double health; // points

    /**
     * Constructor initializing values.
     *
     * @param initialPos Initial position.
     */
    public Wall(Point2D initialPos, double width, double height) {
        super(initialPos, Point2D.ZERO, 0d);
        Map<String, Double> greperConfig = ConfigProvider.getActor(getClass().getSimpleName());
        health = greperConfig.get("health");
        setCollisionGroup(getId().toString());
        setCollisionShape(new CollisionRectangle(initialPos, width, height));
    }

    private void reduceHealth(double damage) {
        health -= damage;
    }
}
