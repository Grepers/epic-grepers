package com.grepers.epicgrepers.world;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.grepers.epicgrepers.collisions.CollisionShape;
import com.grepers.epicgrepers.collisions.events.CollisionListener;
import com.grepers.epicgrepers.config.ConfigProvider;
import javafx.geometry.Point2D;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Base class for every object in the world.
 */
@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type") // class name in json
public abstract class Actor implements CollisionListener {

    private final UUID id; // unique id

    @Setter(AccessLevel.PROTECTED)
    private double health; // points

    @JsonIgnore
    private boolean destroyed = false; // ready to be cleaned up

    private Point2D pos; // meters

    @Setter(AccessLevel.PROTECTED)
    private Point2D vel; // meters per seconds

    @Setter(AccessLevel.PROTECTED)
    private double rot; // radians

    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    private String collisionGroup;

    @Setter(AccessLevel.PROTECTED)
    @JsonIgnore
    private CollisionShape collisionShape;

    /**
     * Constructor initializing parameters.
     *
     * @param initialPos Initial position.
     * @param initialVel Initial velocity.
     * @param initialRot Initial rotation.
     */
    public Actor(Point2D initialPos, Point2D initialVel, double initialRot) {
        id = UUID.randomUUID();
        Map<String, Double> greperConfig = ConfigProvider.getActor(getClass().getSimpleName());
        health = greperConfig.get("health");
        pos = initialPos;
        vel = initialVel;
        rot = initialRot;
    }

    /**
     * Update this Actor and return any generated new Actors.
     *
     * @param elapsedMillis Time passed since last update.
     * @return List of new Actors created, or empty list.
     */
    public List<Actor> update(long elapsedMillis) {
        // update position if needed
        if (!vel.equals(Point2D.ZERO)) {
            pos = pos.add(vel.multiply(elapsedMillis / 1000d));
            collisionShape.update(pos);
        }
        return new ArrayList<>();
    }

    /**
     * Mark this Actor for clean up.
     */
    void destroy() {
        destroyed = true;
    }

    /**
     * Reduce this Actor's health.
     *
     * @param damage Points to damage.
     */
    void damageBy(double damage) {
        health -= damage;
        if (health <= 0)
            destroy();
    }
}
