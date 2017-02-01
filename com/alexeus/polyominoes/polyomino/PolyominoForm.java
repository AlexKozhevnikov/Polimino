package com.alexeus.polyominoes.polyomino;

/**
 * Created by alexeus on 01.02.2017.
 * Класс представляет собой одну конкретную форму полимино
 */
public class PolyominoForm extends Polyomino {

    /**
     * Порядковый номер формы данной фигуры из всего множества поворотов и отражений данного полимино
     */
    private int formNumber;

    private int[] x;

    private int[] y;

    private int maxX, maxY;

    public PolyominoForm(int n, int id, int formNumber, int[] x, int[] y) {
        super(n, id);
        this.formNumber = formNumber;
        this.x = new int[n];
        this.y = new int[n];
        System.arraycopy(x, 0, this.x, 0, n);
        System.arraycopy(y, 0, this.y, 0, n);
        maxX = 0;
        maxY = 0;
        for (int i = 0; i < n; i++) {
            if (x[i] > maxX) {
                maxX = x[i];
            }
            if (y[i] > maxY) {
                maxY = y[i];
            }
        }
    }

    public int getFormNumber() {
        return formNumber;
    }

    @Override
    public String toString() {
        return n + "-" + name + ", форма " + formNumber;
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}
