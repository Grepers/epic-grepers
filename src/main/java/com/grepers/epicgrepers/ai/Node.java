package com.grepers.epicgrepers.ai;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private final List<Node> nodes;
    private final Point2D position;

    public Node(Point2D position) {
        nodes = new ArrayList<>();
        this.position = position;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public Point2D getPosition() {
        return position;
    }

}
