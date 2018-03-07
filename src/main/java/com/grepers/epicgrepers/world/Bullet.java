package com.grepers.epicgrepers.world;

import javafx.geometry.Point2D;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Bullet extends Actor {
    private LocalTime born;
    private Double lifespan; // seconds

    public Bullet(Point2D initialPos, Double initialRot) {
        super(initialPos, new Point2D(sin(initialRot), cos(initialRot)).multiply(3d /*bullet speed*/), initialRot);
        born = LocalTime.now();
        lifespan = 3d;
    }

    @Override
    public List<Actor> update(long elapsedMillis) {
        List<Actor> newActors = super.update(elapsedMillis);
        if(ChronoUnit.MILLIS.between(born, LocalTime.now()) > lifespan * 1000d) {
            destroy();
        }
        return newActors;
    }
}
