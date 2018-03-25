package com.grepers.epicgrepers.ai;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class PathFinder {

    private static final PathFinder instance;

    static {
        instance = new PathFinder();
    }

    public static PathFinder getInstance() {
        return instance;
    }

    /**
     * A* algorithm for path finding
     *
     * @return a list of {@link Point2D} representing the path from point 'a' to point 'b'
     */
    public List<Point2D> AStar(Node node, Point2D start, Point2D goal) {
        List<Point2D> path = new ArrayList<>();

        return path;
    }

    /**
     * Heuristic distance
     *
     * @param current x,y point
     * @param next    x,y point
     * @return double representation of the heuristic distance
     */
    private double heuristic(Point2D current, Point2D next) {
        return Math.abs(current.getX() - next.getX()) + Math.abs(current.getY() - next.getY());
    }

}
