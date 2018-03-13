package com.grepers.epicgrepers.collisions;

import javafx.geometry.Point2D;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Collision shape defining a rectangular area.
 */
@AllArgsConstructor
@Getter
public class CollisionRectangle implements CollisionShape {

    private double top;
    private double right;
    private double bottom;
    private double left;

    private double width;
    private double height;

    /**
     * Constructor initializing values.
     *
     * @param initialPos Initial position.
     * @param width      Rectangle's width.
     * @param height     Rectangles's height.
     */
    public CollisionRectangle(Point2D initialPos, double width, double height) {
        this.width = width;
        this.height = height;
        update(initialPos);
    }

    /**
     * Update rectangle limits.
     *
     * @param newPos Updated position.
     */
    @Override
    public void update(Point2D newPos) {
        double halfHeight = height / 2d;
        double halfWidth = width / 2d;
        top = newPos.getY() + halfHeight;
        right = newPos.getX() + halfWidth;
        bottom = newPos.getY() - halfHeight;
        left = newPos.getY() - halfWidth;
    }
}
