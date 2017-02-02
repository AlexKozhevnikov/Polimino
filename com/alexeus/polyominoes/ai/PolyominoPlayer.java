package com.alexeus.polyominoes.ai;

import com.alexeus.polyominoes.model.Move;
import com.alexeus.polyominoes.model.TargetLine;

import java.util.ArrayList;

/**
 * Created by alexeus on 02.02.2017.
 * Интерфейс для компьютерного движка, который управляет объектом полиомино
 */
public interface PolyominoPlayer {

    /**
     * Представьтесь, пожалуйста!
     * @return %username
     */
    String introduceYourSelf();

    /**
     *
     * @param x             массив с абсциссами квадратов, составляющих "тело" полимины
     * @param y             массив с ординатами квадратов, составляющих "тело" полимины
     * @param possibleForms список из возможных форм, которые может принимать полимино
     * @return ход, который сделал движок
     */
    Move makeMove(int[] x, int[] y, ArrayList<Integer> possibleForms);

    /**
     * Метод задаёт для движка точку-цель, к которой нужно стремиться. Полимино должно стремиться достичь этой цели как можно
     * быстрее. Используется в "гонках".
     * @param x абсцисса цели
     * @param y ордината цели
     */
    void setTargetPoint(int x, int y);

    /**
     * Метод задаёт для движка линию-цель, которую нужно пересечь. Полимино должно стремиться сделать это как можно
     * быстрее. Используется в "гонках".
     * @param line целевая линия
     */
    void setTargetLine(TargetLine line);
}
