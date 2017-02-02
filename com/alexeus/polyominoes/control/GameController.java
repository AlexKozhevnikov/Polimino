package com.alexeus.polyominoes.control;

import com.alexeus.polyominoes.model.PolyMap;
import com.alexeus.polyominoes.polyomino.PolyominoClassifier;

/**
 * Created by alexeus on 02.02.2017.
 * Данный абстрактный класс представляет собой контроллер, задающий правила для игры. Все игры должны наследоваться от него.
 */
public abstract class GameController {

    protected Settings settings;

    // private long timeFromLastInterrupt;

    protected PolyMap map;

    protected PolyominoClassifier classifier;

    protected static final Object controllerMonitor = new Object();

    protected volatile boolean isGameRunning;

    public GameController() {
        settings = Settings.getInstance();
        classifier = PolyominoClassifier.getInstance();
    }

    public void gameCycle() {
        while (isGameRunning) {
            controlPoint();
            doAction();
        }
    }

    protected void controlPoint() {
        try {
            switch (settings.getPlayRegime()) {
                case none:
                    synchronized (controllerMonitor) {
                        controllerMonitor.wait();
                    }
                    break;
                case playEnd:
                    break;
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    protected abstract void doAction();

    public abstract void startGame();

    public static Object getControllerMonitor() {
        return controllerMonitor;
    }

    public void setGameRunning() {
        isGameRunning = true;
    }

    public boolean getGameRunning() {
        return isGameRunning;
    }
}
