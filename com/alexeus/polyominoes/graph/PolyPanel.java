package com.alexeus.polyominoes.graph;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexeus on 01.02.2017.
 * Панель, отрисовывающая поле с полиминами
 */
public class PolyPanel extends JPanel {
    private final float MIN_SCALE = 0.3f;

    private final float MAX_SCALE = 4.8f;

    private float scale = 2.5f;

    PolyPanel() {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        // Рисуем карту
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }

    /**
     * Метод вызывается при изменении масштаба путём прокрутки мышиного колеса. Изменяет масштаб и текущую локацию.
     * @param n количество квантов прокрутки мышиного колеса
     * @param p точка в которой был курсор в момент прокрутки мышиного колеса (относительно него будем центрировать)
     */
    void updatePreferredSize(int n, Point p) {
        double d = Math.pow(1.1, -n);
        double oldScale = scale;
        scale /= d;
        if (scale < MIN_SCALE) {
            scale = MIN_SCALE;
        } else if (scale > MAX_SCALE) {
            scale = MAX_SCALE;
        }
        d = oldScale/ scale;

        int offX = (int)(p.x * d) - p.x;
        int offY = (int)(p.y * d) - p.y;
        setLocation(getLocation().x - offX, getLocation().y - offY);
        getParent().doLayout();

    }
}
