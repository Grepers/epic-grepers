package com.grepers.epicgrepers.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Configuration provider for the world elements.
 */
@Component
@ConfigurationProperties
public class ConfigProvider {

    private static Map<String, Map<String, Double>> actors;

    /**
     * Non static setter.
     *
     * @param actorsConfig Actors config.
     */
    public void setActors(Map<String, Map<String, Double>> actorsConfig) {
        actors = actorsConfig;
    }

    /**
     * Non static getter.
     *
     * @return Actors configs.
     */
    public Map<String, Map<String, Double>> getActors() {
        return actors;
    }

    /**
     * Static accesor.
     *
     * @param actorName Actor configs to retrieve.
     * @return Config.
     */
    public static Map<String, Double> getActor(String actorName) {
        return actors.get(actorName);
    }
}
