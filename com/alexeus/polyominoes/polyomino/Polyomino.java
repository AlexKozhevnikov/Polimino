package com.alexeus.polyominoes.polyomino;

/**
 * Created by alexeus on 01.02.2017.
 * Класс, представляющий собой одну определённую форму полимино
 */
public class Polyomino {

    /**
     * Количество квадратов, формирующих фигуру
     */
    int n;

    /**
     * Порядковый номер фигуры из списка всех фигур с заданным n
     */
    int id;

    /**
     * Название данного полимино
     */
    String name;

    public Polyomino(int n, int id) {
        this.n = n;
        this.id = id;
        name = PolyominoClassifier.getName(n, id);
    }

    public int getN() {
        return n;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return n + "-" + name;
    }
}
