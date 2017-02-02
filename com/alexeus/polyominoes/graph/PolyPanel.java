package com.alexeus.polyominoes.graph;

import com.alexeus.polyominoes.model.PolyMap;
import com.alexeus.polyominoes.model.PolyominoObject;
import com.alexeus.polyominoes.model.TargetLine;
import com.alexeus.polyominoes.model.enums.TerrainType;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexeus on 01.02.2017.
 * Панель, отрисовывающая поле с полиминами
 */
public class PolyPanel extends JPanel {
    private final float MIN_SCALE = 1f;

    private final float MAX_SCALE = 10f;

    private float scale = 2.5f;

    private final static int CELL_SIZE = 50;

    private float trueCellSize = CELL_SIZE / scale;

    private PolyMap map;

    PolyPanel() {
    }

    PolyPanel(PolyMap map) {
        this.map = map;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, 1f));
        // Рисуем карту
        if (map != null) {
            int width = map.getWidth();
            int height = map.getHeight();
            // Рисуем сетку
            for (int i = 1; i < width; i++) {
                g2d.drawLine((int) (i * trueCellSize), 0, (int) (i * trueCellSize), (int) (height * trueCellSize));
            }
            for (int i = 1; i < height; i++) {
                g2d.drawLine(0, (int) (i * trueCellSize), (int) (width * trueCellSize), (int) (i * trueCellSize));
            }
            // Рисуем полимины
            for (PolyominoObject object: map.getPolyominoObject()) {
                g2d.setColor(object.getColor());
                for (int index = 0; index < object.getN(); index++) {
                    g2d.fillRect((int) (object.getX()[index] * trueCellSize), (int) (object.getY()[index] * trueCellSize),
                            (int) (trueCellSize), (int) (trueCellSize));
                }
            }
            // Рисуем ландшафт
            TerrainType terrainType = TerrainType.rock;
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (terrainType != map.getTerrain(x, y)) {
                        terrainType = map.getTerrain(x, y);
                        g2d.setColor(terrainType.getColor());
                    }
                    g2d.fillRect((int) (x * trueCellSize), (int) (y * trueCellSize),
                            (int) (trueCellSize), (int) (trueCellSize));
                }
            }
            // Рисуем дополнительные объекты
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            int x1, x2, y1, y2;
            for (TargetLine line: map.getTargetLines()) {
                if (line.getDirection().getHorizontalInc() == 0) {
                    x1 = line.getX();
                    x2 = line.getX();
                    y1 = 0;
                    y2 = height;
                } else {
                    x1 = 0;
                    x2 = width;
                    y1 = (line.getY() -
                            line.getDirection().getVerticalInc() / line.getDirection().getHorizontalInc() * (line.getX()));
                    y2 = (line.getY() +
                            line.getDirection().getVerticalInc() / line.getDirection().getHorizontalInc() * (width - line.getX()));
                    if (y1 < 0) {
                        x1 = -y1;
                        y1 = 0;
                    }
                    if (y1 > height) {
                        x1 = y1 - height;
                        y1 = height;
                    }
                    if (y2 < 0) {
                        x2 = width - y2;
                        y2 = 0;
                    }
                    if (y2 > height) {
                        x2 = width - y2 + height;
                        y2 = height;
                    }
                }
                g2d.drawLine((int) (x1 * trueCellSize), (int) (y1 * trueCellSize),
                        (int) (x2 * trueCellSize), (int) (y2 * trueCellSize));
            }
        }
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
        trueCellSize = CELL_SIZE / scale;
        int offX = (int)(p.x * d) - p.x;
        int offY = (int)(p.y * d) - p.y;
        setLocation(getLocation().x - offX, getLocation().y - offY);
        getParent().doLayout();
    }
}
