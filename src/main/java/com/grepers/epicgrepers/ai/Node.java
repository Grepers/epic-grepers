package com.grepers.epicgrepers.ai;

import javafx.geometry.Point2D;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Node {

    private final List<Node> nodes;
    private final Point2D position;

    public Node(Point2D position) {
        nodes = new ArrayList<>();
        this.position = position;
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

}
