package com.grepers.epicgrepers.world;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.grepers.epicgrepers.collisions.CollisionDetection;
import javafx.geometry.Point2D;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.util.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * World simulation,
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class World {

    private final CollisionDetection collisionDetection;
    @Getter
    private List<Actor> actors = new ArrayList<>();
    private LocalTime lastUpdate = LocalTime.now();
    @Value("${map}")
    private String map;

    /**
     * Setup this world.
     */
    @PostConstruct
    public final void setup() {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            JsonNode rootNode = objectMapper.readTree(getClass().getResource(map));
            rootNode.fields().forEachRemaining(entry -> {
                //TODO cargar actores desde el mapa en yml se podra hacer con reflection ?
                if (entry.getKey().equals("Wall")) {
                    JsonNode value = entry.getValue();
                    actors.add(new Wall(
                            new Point2D(value.findValue("x").asDouble(), value.findValue("y").asDouble()),
                            value.findValue("width").asDouble(),
                            value.findValue("height").asDouble()));
                }
            });
        } catch (IOException e) {
            log.error("Cannot load map.", e);
        }
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
