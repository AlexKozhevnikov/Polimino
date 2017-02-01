package com.alexeus.polyominoes.graph;

import javax.swing.*;

/**
 * Created by alexeus on 01.02.2017.
 * Окно приложения
 */
public class PolyFrame extends JFrame {
    private static PolyFrame instance;

    private PolyFrame() {
        super("Полимино");
    }

    public static PolyFrame getInstance() {
        if (instance == null) {
            instance = new PolyFrame();
        }
        return instance;
    }
}
