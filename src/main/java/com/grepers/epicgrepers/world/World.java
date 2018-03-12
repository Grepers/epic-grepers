package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionDetection;
import javafx.geometry.Point2D;
import lombok.Getter;
import org.apache.commons.math3.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * World simulation,
 */
@Component
public class World {

    @Getter
    private List<Actor> actors = new ArrayList<>();
    private LocalTime lastUpdate = LocalTime.now();

    @Scheduled(fixedRate = 33)
    public void update() {
        long elapsedMillis = ChronoUnit.MILLIS.between(lastUpdate, LocalTime.now());
        lastUpdate = LocalTime.now();

        // update actors and gather new actors
        List<Actor> newActors = new ArrayList<>();
        actors.forEach(actor -> newActors.addAll(actor.update(elapsedMillis)));

        // handle new actors
        actors.addAll(newActors);
        //TODO inform clients of new actors

        // handle collisions
        List<Pair<Actor, Actor>> colidingActors = checkForCollisions();
        //TODO handle collision events

        // handle destroyed actors
        List<Actor> destroyedActors = actors.stream().filter(Actor::isDestroyed).collect(Collectors.toList());
        actors.removeAll(destroyedActors);
        //TODO inform clients of destroyed actors
    }

    /**
     * Create a new greper.
     *
     * @return Greper created.
     */
    public Greper spawnGreper() {
        Greper greper = new Greper(Point2D.ZERO);
        actors.add(greper);
        return greper;
    }

    private List<Pair<Actor, Actor>> checkForCollisions() {
        //TODO check for collisions ??? this works for any combination of 'areColliding' {@link CollisionShape} impls
        List<Greper> grepers = new ArrayList<>();
        List<Bullet> bullets = new ArrayList<>();

        actors.forEach(a -> {
            if (a instanceof Greper) {
                grepers.add((Greper) (a));
            } else if (a instanceof Bullet) {
                bullets.add((Bullet) (a));
            }
        });

        for (int i = 0; i < grepers.size(); i++) {
            for (int j = 0; j < bullets.size(); j++) {
                // avoid taking health from the bullet's shooter
                if (grepers.get(i).getCollisionGroup().equals(bullets.get(j).getCollisionGroup())) {
                    continue;
                }
                if (CollisionDetection.getInstance().areColliding(grepers.get(i), bullets.get(j))) {
                    // grepers.get(i).reduceHealth(Bullet.DAMAGE); TODO change for the method that reduces health
                }
            }
        }
        return Collections.emptyList();
    }
}
