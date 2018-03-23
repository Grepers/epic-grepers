package com.grepers.epicgrepers.collisions;

import com.grepers.epicgrepers.world.Actor;
import javafx.geometry.Point2D;
import org.apache.commons.math3.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Collision detector for several collision shapes.
 */
@Component
public class CollisionDetection {

    public <A extends Actor> List<Pair<A, A>> checkForCollisions(List<A> actors) {

        List<Pair<A, A>> actorsColliding = new ArrayList<>();

        for (int i = 0; i < actors.size(); i++) {
            for (int j = i; j < actors.size(); j++) {
                if (areColliding(actors.get(i).getCollisionShape(), actors.get(j).getCollisionShape())) {
                    actorsColliding.add(Pair.create(actors.get(i), actors.get(j)));
                }
            }
        }


        return actorsColliding;
    }

    private <S extends CollisionShape> boolean areColliding(S shape1, S shape2) {
        boolean result;
        if (shape1 instanceof CollisionRectangle) {
            if (shape2 instanceof CollisionRectangle) {
                result = areColliding((CollisionRectangle)shape1, (CollisionRectangle)shape2);
            } else {
                result = areColliding((CollisionRectangle)shape1, (CollisionCircle) shape2);
            }
        } else {
            if (shape2 instanceof CollisionRectangle) {
                result = areColliding((CollisionRectangle)shape2, (CollisionCircle)shape1);
            } else {
                result = areColliding((CollisionCircle) shape1, (CollisionCircle)shape2);
            }
        }
        return result;
    }

    private <R extends CollisionRectangle> boolean areColliding(R rect1, R rect2) {
        //TODO cuando dice "* 2" deberia usar doubles ? ( recordar que no se trata de pixels sino metros )
        return (Math.abs(rect1.getLeft() - rect2.getLeft())) * 2 < (Math.abs(rect1.getRight() - rect2.getRight())) &&
                (Math.abs(rect1.getBottom() - rect2.getBottom())) * 2 < (Math.abs(rect1.getTop() - rect2.getTop()));
    }

    private <C extends CollisionCircle> boolean areColliding(C circle1, C circle2) {
        return circle1.getCenter().distance(circle2.getCenter()) < circle1.getRadius() + circle2.getRadius();
    }

    private <C extends CollisionCircle, R extends CollisionRectangle> boolean areColliding(R rect, C circle) {
        //TODO colision entre rect y circle se podra simplificar ?
        Point2D center = circle.getCenter();
        if (pointRectIntersect(rect.getTop(), rect.getLeft(), center.getX(), center.getY())
                || pointRectIntersect(rect.getTop(), rect.getRight(), center.getX(), center.getY())
                || pointRectIntersect(rect.getBottom(), rect.getLeft(), center.getX(), center.getY())
                || pointRectIntersect(rect.getBottom(), rect.getRight(), center.getX(), center.getY())) {
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

    private <C extends CollisionCircle> boolean pointCircleIntersect(C circle, double px, double py) {
        return circle.getCenter().distance(px, py) <= circle.getRadius();
    }

    private boolean pointRectIntersect(double rx, double ry, double px, double py) {
        double distX = px - rx;
        double distY = py - ry;

        return Math.sqrt((distX * distX) + (distY * distY)) <= 0d;
    }
}
