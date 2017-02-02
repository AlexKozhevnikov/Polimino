package com.alexeus.polyominoes.model;

import com.alexeus.polyominoes.model.enums.Direction;

/**
 * Created by alexeus on 02.02.2017.
 * Линия, которую нужно пересечь игроку, чтобы победить.
 */
public class TargetLine {

    private int x, y;

    private Direction direction;

    public TargetLine(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }
}
