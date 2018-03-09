package com.grepers.epicgrepers.collisions;


import java.util.UUID;

/**
 * Basic BoundingBox class that gives you the ability to update the position of the given BoundingBox
 */
public class BoundingBox {

    private static final UUID uuid;

    private final double width;
    private final double height;

    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    static {
        uuid = UUID.randomUUID();
    }

    public BoundingBox(double minX, double minY, double width, double height) {
        this.width = width;
        this.height = height;
        this.minX = minX;
        this.minY = minY;
        this.maxX = minX + width;
        this.maxY = minY + height;
    }

    public static UUID getUuid() {
        return uuid;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void updatePosition(double deltaMinX, double deltaMinY) {
        minX += deltaMinX;
        minY += deltaMinY;
        maxX = minX + width;
        maxY = minY + height;
    }

}
