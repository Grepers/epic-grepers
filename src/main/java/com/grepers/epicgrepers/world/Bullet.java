package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.BoundingBox;
import com.grepers.epicgrepers.collisions.Collidable;

import javafx.geometry.Point2D;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Bullet extends Actor implements Collidable {

    public static final int WIDTH = 6;
    public static final int HEIGHT = 6;
    public static final int DAMAGE = 10;
    // 'flag' variable to avoid hitting the bullet owner
    private UUID shooterId;

    private LocalTime born;
    private BoundingBox boundingBox;
    private Double lifespan; // seconds

    public Bullet(Point2D initialPos, Double initialRot) {
        this(null, initialPos, initialRot);
    }

    public Bullet(UUID shooterId, Point2D initialPos, Double initialRot) {
        super(initialPos, new Point2D(sin(initialRot), cos(initialRot)).multiply(3d /*bullet speed*/), initialRot);
        this.shooterId = shooterId;
        born = LocalTime.now();
        boundingBox = new BoundingBox(initialPos.getX() - (WIDTH / 2), initialPos.getY() - (HEIGHT - 2), WIDTH, HEIGHT);
        lifespan = 3d;
    }

    @Override
    public List<Actor> update(long elapsedMillis) {
        List<Actor> newActors = super.update(elapsedMillis);
        if(ChronoUnit.MILLIS.between(born, LocalTime.now()) > lifespan * 1000d) {
            destroy();
        }
        boundingBox.updatePosition(getPos().getX(), getPos().getY());
        return newActors;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public UUID getShooterId() { return shooterId; }

}
