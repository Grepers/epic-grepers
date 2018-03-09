package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.Collisions;
import javafx.geometry.Point2D;
import lombok.Getter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        checkForCollisions();
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

    private void checkForCollisions() {
        List<Greper> grepers = new ArrayList<>();
        List<Bullet> bullets = new ArrayList<>();

        actors.forEach(a -> {
            if (a instanceof Greper) {
                grepers.add((Greper)(a));
            } else if (a instanceof Bullet) {
                bullets.add((Bullet)(a));
            }
        });

        for (int i = 0; i < grepers.size(); i++) {
            for (int j = 0; j < bullets.size(); j++) {
                // avoid taking health from the bullet's shooter
                if (grepers.get(i).getId().equals(bullets.get(j).getShooterId())) {
                    continue;
                }
                if (Collisions.getInstance().areColliding(grepers.get(i), bullets.get(j))) {
                    grepers.get(i).reduceHealth(Bullet.DAMAGE);
                }
            }
        }

    }
}
