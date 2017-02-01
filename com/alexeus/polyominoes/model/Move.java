package com.alexeus.polyominoes.model;

/**
 * Created by alexeus on 02.02.2017.
 * Движение - вот лучшее учение!
 */
public class Move {

    private final int fromX, fromY, toX, toY;

    public Move(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public int getFromX() {
        return fromX;
    }

    public int getFromY() {
        return fromY;
    }

    public int getToX() {
        return toX;
    }

    public int getToY() {
        return toY;
    }
}
