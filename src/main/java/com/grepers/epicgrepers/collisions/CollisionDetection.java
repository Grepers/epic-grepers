package com.grepers.epicgrepers.collisions;

/**
 * Collision detector for several collision shapes.
 */
public class CollisionDetection {
    /**
     * No need to instantiate this class.
     */
    private CollisionDetection() {
    }

    public static boolean areColliding(CollisionShape shapeA, CollisionShape shapeB) {
        if (shapeA instanceof CollisionRectangle && shapeB instanceof CollisionRectangle)
            return areCollidingRectangles((CollisionRectangle) shapeA, (CollisionRectangle) shapeB);
        else if (shapeA instanceof CollisionCircle && shapeB instanceof CollisionCircle)
            return areCollidingCircles((CollisionCircle) shapeA, (CollisionCircle) shapeB);
        else if (shapeA instanceof CollisionRectangle && shapeB instanceof CollisionCircle)
            return areCollidingRectangleCircle((CollisionRectangle) shapeA, (CollisionCircle) shapeB);
        else if (shapeA instanceof CollisionCircle && shapeB instanceof CollisionRectangle)
            return areCollidingRectangleCircle((CollisionRectangle) shapeB, (CollisionCircle) shapeA);
        else
            return false;
    }

    private static boolean areCollidingRectangleCircle(CollisionRectangle shapeA, CollisionCircle shapeB) {
        //TODO check collision between rectangle and circle
        return false;
    }

    private static boolean areCollidingCircles(CollisionCircle shapeA, CollisionCircle shapeB) {
        //TODO check collision between circles
        return false;
    }

    /**
     * Check collision between two rectangles.
     *
     * @param rectA Rectangle A.
     * @param rectB Rectangle B.
     * @return True if colliding.
     */
    public static boolean areCollidingRectangles(CollisionRectangle rectA, CollisionRectangle rectB) {
        //TODO check collision between rectangles
//        return (Math.abs(rectA.getMinX() - rectB.getMinX())) * 2 < (Math.abs(rectA.getMaxX() - rectB.getMaxX())) &&
//                (Math.abs(rectA.getMinY() - rectB.getMinY())) * 2 < (Math.abs(rectA.getMaxY() - rectB.getMaxY()));
        return false;
    }
}
