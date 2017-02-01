package com.alexeus.polyominoes.model;

import com.alexeus.polyominoes.ai.PolyominoPlayer;
import com.alexeus.polyominoes.model.enums.TerrainType;

import java.util.ArrayList;

import static com.alexeus.polyominoes.polyomino.PolyominoClassifier.MAX_POLYOMINO_SIZE;

/**
 * Created by alexeus on 02.02.2017.
 * Карта, по которой перемещаются полиомины и другие объекты.
 */
public class PolyMap {

    private int width, height;

    private TerrainType[][] terrain;

    private ArrayList<PolyominoObject> polyominoObject;

    public PolyMap(int width, int height) {
        this.width = width;
        this.height = height;
        terrain = new TerrainType[width][height];
        polyominoObject = new ArrayList<>();
    }

    /**
     *
     * @param n      количество квадратов
     * @param x      абсциссы квадратов
     * @param y      ординаты квадратов
     * @param player движок полиомино
     * @throws IllegalArgumentException если подали плохие аргументы
     * @return true, если удалось успешно
     */
    public boolean addPolyomino(int n, int[] x, int[] y, PolyominoPlayer player) {
        if (x == null || y == null || n > MAX_POLYOMINO_SIZE || x.length > n || y.length > n) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < n; i++) {
            if (!terrain[x[i]][y[i]].isPassable()) {
                return false;
            }
        }
        PolyominoObject object = new PolyominoObject(n, x, y, player);
        return true;
    }
}
