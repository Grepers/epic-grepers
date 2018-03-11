package com.grepers.epicgrepers.dto;

import javafx.geometry.Point2D;
import lombok.Data;

/**
 * Message from client.
 */
@Data
public class MessageFromClient {
    private Point2D vel;
    private Double rot;
    private Boolean firing;
}