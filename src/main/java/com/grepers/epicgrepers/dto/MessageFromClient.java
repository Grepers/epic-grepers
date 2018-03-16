package com.grepers.epicgrepers.dto;

import com.sun.javafx.geom.Vec2d;
import javafx.geometry.Point2D;
import lombok.Data;

/**
 * Message from client.
 */
@Data
public class MessageFromClient {
    private Vec2d vel;
    private double rot;
    private boolean firing;
}
