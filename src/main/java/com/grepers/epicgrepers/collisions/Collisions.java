package com.grepers.epicgrepers.collisions;

import javafx.geometry.BoundingBox;

import java.util.Objects;

public class Collisions {

    private static final Collisions instance;

    static {
        instance = new Collisions();
    }

    private Collisions() {}

    public static Collisions getInstance() { return instance; }

    public <C extends Collidable> boolean areColliding(C collider1, C collider2) {
        BoundingBox boundingBox1 = collider1.getBoundingBox();
        BoundingBox boundingBox2 = collider2.getBoundingBox();
        Objects.requireNonNull(boundingBox1);
        Objects.requireNonNull(boundingBox2);

        return (Math.abs(boundingBox1.getMinX() - boundingBox2.getMinX())) * 2 < (Math.abs(boundingBox1.getWidth() - boundingBox2.getWidth())) &&
                (Math.abs(boundingBox1.getMinY() - boundingBox2.getMinY())) * 2 < (Math.abs(boundingBox1.getHeight() - boundingBox2.getHeight()));
    }
}
