package com.alexeus.polyominoes.polyomino;

import com.alexeus.polyominoes.model.Move;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alexeus on 02.02.2017.
 * Данный класс просчитывает все возможные ходы и выдаёт списки возможных ходов игрокам, когда они в этом нуждаются.
 */
public class MoveCalculator {

    public static final int MIN_WALKABLE_POLYOMINO_SIZE = 4;

    private static MoveCalculator instance;

    private PolyominoClassifier classifier;

    /**
     * Карта, в которой хранятся списки ходов для любой пары <прошлая форма - новая фигура>.
     * Из этой пары сначала нужно сделать int вызовом метода getPairCode()
     */
    private HashMap<Integer, HashMap<Integer, ArrayList<Move>>> possibleMoves;

    private MoveCalculator() {
        classifier = PolyominoClassifier.getInstance();
        possibleMoves = new HashMap<>();
        fillPossibleMoves();
    }

    public static MoveCalculator getInstance() {
        if (instance == null) {
            instance = new MoveCalculator();
        }
        return instance;
    }

    private void fillPossibleMoves() {
        int[] x, y, changedX, changedY;
        for (int n = MIN_WALKABLE_POLYOMINO_SIZE; n <= PolyominoClassifier.MAX_POLYOMINO_SIZE; n++) {
            possibleMoves.put(n, new HashMap<>());
            changedX = new int[n];
            changedY = new int[n];
            for (ArrayList<PolyominoForm> listOfForms: classifier.getPolyominoFormsOfSize(n)) {
                for (PolyominoForm exForm: listOfForms) {
                    x = exForm.getX();
                    y = exForm.getY();
                    for (Polyomino newPoly: classifier.getPolyominoesOfSize(n)) {
                        int pairCode = getPairCode(exForm, newPoly);
                        // Пытаемся построить ход, для этого проверяем все квадраты
                        for (int movingSquareIndex = 0; movingSquareIndex < n; movingSquareIndex++) {
                            System.arraycopy(x, 0, changedX, 0, n);
                            System.arraycopy(y, 0, changedY, 0, n);
                            // берём все остальные квадраты и пытаемся присобачить движущийся квадрат к ним
                            for (int squareToIndex = 0; squareToIndex < n; squareToIndex++) {
                                if (movingSquareIndex == squareToIndex) continue;
                                changedX[movingSquareIndex] = x[squareToIndex] - 1;
                                changedY[movingSquareIndex] = y[squareToIndex];
                                tryToMakeMove(x[movingSquareIndex], y[movingSquareIndex],
                                        changedX, changedY, movingSquareIndex, pairCode, newPoly);
                                changedX[movingSquareIndex] = x[squareToIndex] + 1;
                                tryToMakeMove(x[movingSquareIndex], y[movingSquareIndex],
                                        changedX, changedY, movingSquareIndex, pairCode, newPoly);
                                changedX[movingSquareIndex] = x[squareToIndex];
                                changedY[movingSquareIndex] = y[squareToIndex] - 1;
                                tryToMakeMove(x[movingSquareIndex], y[movingSquareIndex],
                                        changedX, changedY, movingSquareIndex, pairCode, newPoly);
                                changedY[movingSquareIndex] = y[squareToIndex] + 1;
                                tryToMakeMove(x[movingSquareIndex], y[movingSquareIndex],
                                        changedX, changedY, movingSquareIndex, pairCode, newPoly);
                            }
                        }
                    }
                }
            }
        }
    }

    private void tryToMakeMove(int prevX, int prevY, int[] x, int[] y, int movingSquareIndex, int pairCode,
                               Polyomino targetPolyomino) {
        int n = targetPolyomino.getN();
        boolean isConnected = false;
        int xIndent = x[movingSquareIndex] == -1 ? 1 : 0, yIndent = y[movingSquareIndex] == -1 ? 1 : 0;
        boolean leftFlag = true, upFlag = true;
        for (int i = 0; i < n - 1; i++) {
            if (x[i] == 0) {
                leftFlag = false;
            }
            if (y[i] == 0) {
                upFlag = false;
            }
            if (i == movingSquareIndex) continue;
            if (x[i] == x[movingSquareIndex] && y[i] == y[movingSquareIndex]) {
                return;
            }
            if (Math.abs(x[i] - x[movingSquareIndex]) + Math.abs(y[i] - y[movingSquareIndex]) == 1) {
                isConnected = true;
            }
        }
        if (upFlag) yIndent = -1;
        if (leftFlag) xIndent = -1;
        if (isConnected) {
            if (xIndent != 0) {
                for (int i = 0; i < n; i++) {
                    x[i] = x[i] + xIndent;
                }
            }
            if (yIndent != 0) {
                for (int i = 0; i < n; i++) {
                    y[i] = y[i] + yIndent;
                }
            }
            PolyominoForm form = classifier.detectForm(n, x, y);
            if (form != null && form.getPolyominoId() == targetPolyomino.getPolyominoId()) {
                // Успех, осздаём ход
                Move move = new Move(prevX, prevY, x[movingSquareIndex] - xIndent, y[movingSquareIndex] - yIndent);
                if (!possibleMoves.get(n).containsKey(pairCode)) {
                    possibleMoves.get(n).put(pairCode, new ArrayList<>());
                }
                possibleMoves.get(n).get(pairCode).add(move);
            }
        }
    }

    private int getPairCode(PolyominoForm exForm, Polyomino newPoly) {
        int numPolyominoes = classifier.getNumPolyominos(newPoly.getN());
        return (exForm.getFormNumber() * numPolyominoes + newPoly.getPolyominoId()) *
                PolyominoClassifier.MAX_POLYOMINO_SIZE + exForm.getN();
    }
}
