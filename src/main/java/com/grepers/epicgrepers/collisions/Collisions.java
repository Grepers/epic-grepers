package com.grepers.epicgrepers.collisions;

import javafx.geometry.BoundingBox;

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

        return ((
                (boundingBox1.getMinX() >= boundingBox2.getMinX() - boundingBox2.getWidth()) &&
                (boundingBox1.getMinX() <= boundingBox2.getMinX() + boundingBox2.getWidth())
        ) && (
                (boundingBox1.getMinY() >= boundingBox2.getMinY() - boundingBox2.getHeight()) &&
                (boundingBox1.getMinY() <= boundingBox2.getMinY() + boundingBox2.getHeight())
        ));
    }
}
