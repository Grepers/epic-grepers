package com.grepers.epicgrepers.collisions;

import com.grepers.epicgrepers.world.Actor;
import javafx.geometry.Point2D;
import org.apache.commons.math3.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Collision detector for several collision shapes.
 */
@Component
public class CollisionDetection {

    public List<Pair<Actor, Actor>> checkForCollisions(List<Actor> actors) {
        //TODO verificar todos con todos y devolver la lista, despues World resolvera los eventos.
        //TODO tener en cuenta que entre miembros del mismo collisionGroup no se colisionan

        //        //TODO check for collisions ??? this works for any combination of 'areColliding' {@link CollisionShape} impls
        //        List<Greper> grepers = new ArrayList<>();
        //        List<Bullet> bullets = new ArrayList<>();
        //
        //        actors.forEach(a -> {
        //            if (a instanceof Greper) {
        //                grepers.add((Greper) (a));
        //            } else if (a instanceof Bullet) {
        //                bullets.add((Bullet) (a));
        //            }
        //        });
        //
        //        for (int i = 0; i < grepers.size(); i++) {
        //            for (int j = 0; j < bullets.size(); j++) {
        //                // avoid taking health from the bullet's shooter
        //                if (grepers.get(i).getCollisionGroup().equals(bullets.get(j).getCollisionGroup())) {
        //                    continue;
        //                }
        //                if (CollisionDetection.getInstance().areColliding(grepers.get(i), bullets.get(j))) {
        //                    // grepers.get(i).reduceHealth(Bullet.DAMAGE); TODO change for the method that reduces health
        //                }
        //            }
        //        }
        return Collections.emptyList();
    }

    public <R extends CollisionRectangle> boolean areColliding(R rect1, R rect2) {
        //TODO cuando dice "* 2" deberia usar doubles ? ( recordar que no se trata de pixels sino metros )
        return (Math.abs(rect1.getLeft() - rect2.getLeft())) * 2 < (Math.abs(rect1.getRight() - rect2.getRight())) &&
                (Math.abs(rect1.getBottom() - rect2.getBottom())) * 2 < (Math.abs(rect1.getTop() - rect2.getTop()));
    }

    public <C extends CollisionCircle> boolean areColliding(C circle1, C circle2) {
        return circle1.getCenter().distance(circle2.getCenter()) < circle1.getRadius() + circle2.getRadius();
    }

    public <C extends CollisionCircle, R extends CollisionRectangle> boolean areColliding(R rect, C circle) {
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
