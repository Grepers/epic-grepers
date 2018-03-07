package com.grepers.epicgrepers.world;

import javafx.geometry.Point2D;
import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Component
public class World {
    @Getter
    private List<Actor> actors = new ArrayList<>();
    private LocalTime lastUpdate = LocalTime.now();

    @Scheduled(fixedRate = 33)
    public void update() {
        long elapsedMillis = ChronoUnit.MILLIS.between(lastUpdate, LocalTime.now());
        lastUpdate = LocalTime.now();
        List<Actor> newActors = new ArrayList<>();
        actors.forEach(actor -> newActors.addAll(actor.update(elapsedMillis)));
        actors.addAll(newActors);
        actors.removeIf(Actor::isDestroyed);
    }

    public Greper spawnGreper() {
        Greper greper = new Greper(Point2D.ZERO, Point2D.ZERO.add(1,0), 0d, "zero");
        actors.add(greper);
        return greper;
    }

    public void killGreper(Greper greper) {
        greper.kill();
    }
}
