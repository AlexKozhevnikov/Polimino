package com.alexeus.polyominoes.control;

import com.alexeus.polyominoes.ai.PolyominoPlayer;
import com.alexeus.polyominoes.model.Move;
import com.alexeus.polyominoes.model.PolyMap;
import com.alexeus.polyominoes.model.TargetLine;
import com.alexeus.polyominoes.model.enums.Direction;
import com.alexeus.polyominoes.polyomino.Polyomino;
import com.alexeus.polyominoes.polyomino.PolyominoClassifier;
import com.alexeus.polyominoes.polyomino.PolyominoForm;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by alexeus on 02.02.2017.
 * Класс разыгрывает гонку полиомино - одну из самых простых игр, которые можно придумать для полимин.
 */
public class PolyominoRace extends GameController {

    private static int WIDTH = 50, HEIGHT = 50;
    private static int NUM_PLAYER = 2;
    private static int POLYOMINO_SIZE = 4;
    private static int INDENT = 5;

    private ArrayList<PolyominoPlayer> player;

    private PolyMap map;

    private int[] x, y;

    public PolyominoRace() {
        super();
        x = new int[POLYOMINO_SIZE];
        y = new int[POLYOMINO_SIZE];
    }

    @Override
    protected void doAction() {

    }

    @Override
    public void startGame() {
        map = new PolyMap(WIDTH, HEIGHT);
        map.setStandardTerrain();
        TargetLine line = new TargetLine(25, 3, Direction.east);
        map.addTargetLine(line);
        player = new ArrayList<>();
        PolyominoForm beginForm = classifier.getForm(POLYOMINO_SIZE, 1, 5);
        for (int i = 0; i < NUM_PLAYER; i++) {
            int xFrom = (int) (1f * WIDTH / (NUM_PLAYER + 1));
            int yFrom = HEIGHT - INDENT;
            System.arraycopy(beginForm.getX(), 0, x, 0, POLYOMINO_SIZE);
            System.arraycopy(beginForm.getY(), 0, y, 0, POLYOMINO_SIZE);
            for (int j = 0; j < POLYOMINO_SIZE; j++) {
                x[j] += xFrom;
                y[j] += yFrom;
            }
            map.addPolyomino(POLYOMINO_SIZE, x, y, i == 0 ? Color.RED : Color.BLUE);
            player.add(new RacePlayer());
            player.get(i).setTargetLine(line);
        }
    }
}
