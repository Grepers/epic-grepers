package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.Collidable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;
import lombok.Getter;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
public class Greper extends Actor implements Collidable {
    private static final int WIDTH = 15;
    private static final int HEIGHT = 15;

    private BoundingBox boundingBox;
    private Double health;
    private String name;
    private boolean firing;
    private Double firerate; // rounds per second
    private LocalTime lastFiredTime;

    public Greper(Point2D initialPos, Point2D initialVel, Double initialRot, String name) {
        super(initialPos, initialVel, initialRot);
        this.name = name;
        health = 100d;
        firing = true;
        firerate = 10d;
        lastFiredTime = LocalTime.now();
        boundingBox = new BoundingBox(initialPos.getX(), initialPos.getY(), WIDTH, HEIGHT);
    }

    public void kill() {
        health = 0d;
    }

    @Override
    public List<Actor> update(long elapsedMillis) {
        List<Actor> newActors = super.update(elapsedMillis);
        if (firing) {
            if(ChronoUnit.MILLIS.between(lastFiredTime, LocalTime.now()) > 1000d / firerate) {
                newActors.add(new Bullet(getPos(), getRot()));
                lastFiredTime = LocalTime.now();
            }
        }
        return newActors;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }
}
