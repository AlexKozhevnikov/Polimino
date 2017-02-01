package com.alexeus.polyominoes.graph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by alexeus on 01.02.2017.
 * Основная панель.
 */
public class MainPanel extends JPanel {

    private static final int LEFT_PANEL_WIDTH = 500;
    private static final int BUTTON_PANEL_HEIGHT = 60;
    private static final int ICON_SIZE = 50;
    private static final int CANVAS_WIDTH = 800, CANVAS_HEIGHT = 600;

    private PolyPanel mapPanel;
    private ImageIcon playEndIcon, nextIcon;
    //private ImageIcon collapseIcon, returnIcon;
    //private JButton leftPanelCollapser;

    public MainPanel() {
        setLayout(new BorderLayout());
        nextIcon = loadIcon("C:\\Pictures\\next.png", ICON_SIZE);
        playEndIcon = loadIcon("C:\\Pictures\\playEnd.png", ICON_SIZE);
        // Отрисовка основной панели игры
        mapPanel = new PolyPanel();
        mapPanel.setAutoscrolls(true);
        JScrollPane scrollPane = new JScrollPane(mapPanel);
        scrollPane.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            private Point origin;
            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, mapPanel);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        mapPanel.scrollRectToVisible(view);
                    }
                }
            }
        };
        mapPanel.addMouseListener(mouseAdapter);
        mapPanel.addMouseMotionListener(mouseAdapter);
        mapPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                mapPanel.updatePreferredSize(e.getWheelRotation(), e.getPoint());
            }}
        );
        add(scrollPane, BorderLayout.CENTER);
        // отрисовка элементов левой панели
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        InfoPanel infoPanel = new InfoPanel();
        infoPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, CANVAS_HEIGHT - BUTTON_PANEL_HEIGHT));
        leftPanel.add(infoPanel, BorderLayout.PAGE_END);

        JPanel buttonsPanel = new JPanel(new FlowLayout());
        JButton nextButton = new JButton("", nextIcon);
        nextButton.setPreferredSize(new Dimension(ICON_SIZE, ICON_SIZE));

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                synchronized (Controller.getControllerMonitor()) {
                    Controller.getControllerMonitor().notify();
                }
            }
        });

        JButton playEndButton = new JButton("", playEndIcon);
        playEndButton.setPreferredSize(new Dimension(ICON_SIZE, ICON_SIZE));
        playEndButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings.getInstance().setPlayRegime(PlayRegimeType.playEnd);
                synchronized (Controller.getControllerMonitor()) {
                    Controller.getControllerMonitor().notify();
                }
            }
        });
        buttonsPanel.add(nextButton);
        buttonsPanel.add(playEndButton);
        buttonsPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, BUTTON_PANEL_HEIGHT));
        leftPanel.add(buttonsPanel, BorderLayout.PAGE_START);
        add(leftPanel, BorderLayout.LINE_END);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(50);
                    repaint();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Controller.getInstance().startController();
            }
        };
        t.start();
    }

    private ImageIcon loadIcon(String path, int size) {
        File file = new File(path);
        BufferedImage image;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        BufferedImage resizedImg = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(image, 0, 0, size, size, null);
        g2.dispose();
        return new ImageIcon(resizedImg);
    }
}
