package com.grepers.epicgrepers.world;

import com.grepers.epicgrepers.collisions.CollisionDetection;
import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * World simulation,
 */
@RequiredArgsConstructor
@Component
public class World {

    private final CollisionDetection collisionDetection;
    @Getter
    private List<Actor> actors = new ArrayList<>();
    private LocalTime lastUpdate = LocalTime.now();
    @Value("${map}")
    private String map;

    @PostConstruct
    public final void setup() {
        //TODO load map
    }

    /**
     * Update the world.
     */
    @Scheduled(fixedRate = 33)
    public void update() {
        // calculate elapsed time
        long elapsedMillis = ChronoUnit.MILLIS.between(lastUpdate, LocalTime.now());
        lastUpdate = LocalTime.now();

        // update actors and gather new actors
        List<Actor> newActors = new ArrayList<>();
        actors.forEach(actor -> newActors.addAll(actor.update(elapsedMillis)));

        // handle new actors
        actors.addAll(newActors);
        //TODO inform clients of new actors

        // handle collisions
        List<Pair<Actor, Actor>> colidingActors = collisionDetection.checkForCollisions(actors);
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
}
