package com.alexeus.polyominoes.model.enums;

/**
 * Created by alexeus on 02.02.2017.
 * Перечисление направлений
 */
public enum Direction {
    north,
    northEast,
    east,
    southEast,
    south,
    southWest,
    west,
    northWest;

    public int getHorizontalInc() {
        switch (this) {
            case east:
            case northEast:
            case southEast:
                return 1;
            case west:
            case southWest:
            case northWest:
                return -1;
            default:
                return 0;
        }
    }

    public int getVerticalInc() {
        switch (this) {
            case north:
            case northEast:
            case northWest:
                return -1;
            case south:
            case southWest:
            case southEast:
                return 1;
            default:
                return 0;
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case north:
                return "Север";
            case northEast:
                return "Северо-восток";
            case east:
                return "Восток";
            case southEast:
                return "Юго-восток";
            case south:
                return "Юг";
            case southWest:
                return "Юго-запад";
            case west:
                return "Запад";
            case northWest:
                return "Северо-запад";
        }
        return null;
    }
}
