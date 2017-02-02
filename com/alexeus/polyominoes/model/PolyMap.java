package com.alexeus.polyominoes.model;

import com.alexeus.polyominoes.model.enums.TerrainType;

import java.awt.*;
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

    private ArrayList<TargetLine> targetLines;

    public PolyMap(int width, int height) {
        this.width = width;
        this.height = height;
        terrain = new TerrainType[width][height];
        polyominoObject = new ArrayList<>();
        targetLines = new ArrayList<>();
    }

    public void setStandardTerrain() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                terrain[i][j] = (i == 0 || j == 0 || i == width - 1 || j == height - 1) ?
                        TerrainType.rock : TerrainType.free;
            }
        }
    }

    public void addTargetLine(TargetLine line) {
        targetLines.add(line);
    }

    public ArrayList<TargetLine> getTargetLines() {
        return targetLines;
    }

    /**
     * Метод добавляет полимину на карту
     * @param n      количество квадратов
     * @param x      абсциссы квадратов
     * @param y      ординаты квадратов
     * @throws IllegalArgumentException если подали плохие аргументы
     * @return true, если удалось успешно
     */
    public boolean addPolyomino(int n, int[] x, int[] y, Color color) {
        if (x == null || y == null || n > MAX_POLYOMINO_SIZE || x.length > n || y.length > n) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < n; i++) {
            if (!terrain[x[i]][y[i]].isPassable()) {
                return false;
            }
        }
        PolyominoObject object = new PolyominoObject(n, x, y, color);
        return true;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TerrainType[][] getTerrain() {
        return terrain;
    }

    public TerrainType getTerrain(int x, int y) {
        return terrain[x][y];
    }

    public ArrayList<PolyominoObject> getPolyominoObject() {
        return polyominoObject;
    }

    public PolyominoObject getPolyominoObject(int index) {
        return polyominoObject.get(index);
    }
}
