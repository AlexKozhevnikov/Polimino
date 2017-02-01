package com.alexeus.polyominoes.model.enums;

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
}
