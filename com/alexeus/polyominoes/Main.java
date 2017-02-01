package com.alexeus.polyominoes;

import com.alexeus.polyominoes.graph.MainPanel;
import com.alexeus.polyominoes.graph.PolyFrame;
import com.alexeus.polyominoes.polyomino.PolyominoClassifier;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static void main(String[] args) {
        //printFonts();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = PolyFrame.getInstance();
                frame.setContentPane(new MainPanel());
                addMenu(frame);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    private static void addMenu(JFrame frame) {
        Font font = new Font("Verdana", Font.PLAIN, 11);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        fileMenu.setFont(font);

        JMenuItem newGameItemMenu = new JMenuItem("Новая игра");
        newGameItemMenu.setFont(font);
        fileMenu.add(newGameItemMenu);

        JMenuItem exitItem = new JMenuItem("Выйти");
        exitItem.setFont(font);
        fileMenu.add(exitItem);

        newGameItemMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Controller.getInstance().getGameRunning()) {
                    try {
                        Controller.getInstance().setGameStatus(GameStatus.interrupted);
                        synchronized (Controller.getControllerMonitor()) {
                            Controller.getControllerMonitor().notify();
                            Controller.getControllerMonitor().wait();
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                synchronized (Controller.getControllerMonitor()) {
                    Controller.getInstance().setGameRunning();
                    Controller.getControllerMonitor().notify();
                }
            }
        });

        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuBar.add(fileMenu);

        frame.setJMenuBar(menuBar);
    }
}
