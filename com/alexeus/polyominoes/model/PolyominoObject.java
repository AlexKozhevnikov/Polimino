package com.alexeus.polyominoes.model;

import java.awt.*;

/**
 * Created by alexeus on 02.02.2017.
 * Класс представляет собой движущийся объект полиомино.
 */
public class PolyominoObject {

    private int n;

    private int[] x, y;

    private Color color;

    public PolyominoObject(int n, int[] x, int[] y, Color color) {
        this.n = n;
        this.x = new int[n];
        this.y = new int[n];
        this.color = color;
        System.arraycopy(x, 0, this.x, 0, n);
        System.arraycopy(y, 0, this.y, 0, n);
    }

    /**
     * Данный метод заставляет объект переместить один из своих квадратов.
     * @param fromX абсцисса перемещаемого квадрата
     * @param fromY ордината перемещаемого квадрата
     * @param toX   абсцисса места назначения
     * @param toY   ордината места назначения
     * @return true, если удалось найти в данной полиомине искомый квадрат
     */
    public boolean makeMove(int fromX, int fromY, int toX, int toY) {
        for (int i = 0; i < n; i++) {
            if (x[i] == fromX && y[i] == fromY) {
                x[i] = toX;
                y[i] = toY;
                return true;
            }
        }
        return false;
    }

    /**
     * Данный метод заставляет объект совершить ход.
     * @param move ход, который надлежит исполнить
     * @return true, если удалось найти в данной полиомине искомый квадрат хода
     */
    public boolean makeMove(Move move) {
        for (int i = 0; i < n; i++) {
            if (x[i] == move.getFromX() && y[i] == move.getFromY()) {
                x[i] = move.getToX();
                y[i] = move.getToY();
                return true;
            }
        }
        return false;
    }

    public Color getColor() {
        return color;
    }

    public int getN() {
        return n;
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }
}
