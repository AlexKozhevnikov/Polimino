package com.alexeus.polyominoes.model.enums;

import java.awt.*;

/**
 * Created by alexeus on 02.02.2017.
 * Тип ячейки карты
 */
public enum TerrainType {
    free,
    rock,
    dirt;

    public boolean isPassable() {
        return this == free;
    }

    public Color getColor() {
        switch (this) {
            case free:
                return Color.lightGray;
            case rock:
                return Color.darkGray;
            case dirt:
                return Color.ORANGE;
        }
        return null;
    }
}
