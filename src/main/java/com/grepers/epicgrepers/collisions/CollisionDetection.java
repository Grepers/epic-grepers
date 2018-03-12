package com.grepers.epicgrepers.collisions;

/**
 * Collision detector for several collision shapes.
 */
public class CollisionDetection {

    private static final CollisionDetection instance;
    private static final double ZERO_RADIUS = 0.0;

    static {
        instance = new CollisionDetection();
    }

    public static CollisionDetection getInstance() { return instance; }

    /**
     * No need to instantiate this class.
     */
    private CollisionDetection() {
    }

    public <CR extends CollisionRectangle> boolean areColliding(CR rect1, CR rect2) {
        return (Math.abs(rect1.getLeft() - rect2.getLeft())) * 2 < (Math.abs(rect1.getRight() - rect2.getRight())) &&
                (Math.abs(rect1.getBottom() - rect2.getBottom())) * 2 < (Math.abs(rect1.getTop() - rect2.getTop()));
    }

    public <CC extends CollisionCircle> boolean areColliding(CC circle1, CC circle2) {
        double xDif = circle1.getX() - circle2.getX();
        double yDif = circle1.getY() - circle2.getY();
        double squaredDistance = ( xDif * xDif ) + ( yDif * yDif );

        return ( squaredDistance < (circle1.getRadius() + circle2.getRadius()) * (circle1.getRadius() + circle2.getRadius()) );
    }

    public <CC extends CollisionCircle, CR extends CollisionRectangle> boolean areColliding(CR rect, CC circle) {
        if (pointRectIntersect(rect.getTop(), rect.getLeft(), circle.getX(), circle.getY())
                || pointRectIntersect(rect.getTop(), rect.getRight(), circle.getX(), circle.getY())
                || pointRectIntersect(rect.getBottom(), rect.getLeft(), circle.getX(), circle.getY())
                || pointRectIntersect(rect.getBottom(), rect.getRight(), circle.getX(), circle.getY())) {
            return true;
        }
        if (pointCircleIntersect(circle, rect.getTop(), rect.getLeft())
                || pointCircleIntersect(circle, rect.getTop(), rect.getRight())
                || pointCircleIntersect(circle, rect.getBottom(), rect.getLeft())
                || pointCircleIntersect(circle, rect.getBottom(), rect.getRight())) {
            return true;
        }
        return false;
    }

    private <CC extends CollisionCircle> boolean pointCircleIntersect(CC circle, double px, double py) {
        double distX = px - circle.getX();
        double distY = py - circle.getY();

        return Math.sqrt((distX*distX) + (distY*distY)) <= circle.getRadius();
    }

    private boolean pointRectIntersect(double rx, double ry, double px, double py) {
        double distX = px - rx;
        double distY = py - ry;

        return Math.sqrt((distX*distX) + (distY*distY)) <= ZERO_RADIUS;
    }

}
