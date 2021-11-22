package pl.pacinho.pacman.view.cells;

import pl.pacinho.pacman.model.CellType;

import javax.swing.*;
import java.awt.*;

public class PointCell extends Cell {

    private ImageIcon image;

    public PointCell() {
        this.cellType = CellType.POINT;
        init();
    }

    private void init() {
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int oHeight = (int) (getHeight() * 0.1);
        int oWidth = (int) (getWidth() * 0.1);
        int x = (getHeight() - oHeight) / 2;
        int y = (getWidth() - oWidth) / 2;
        g.setColor(Color.PINK);
        g.fillRect(x, y, oHeight, oWidth);
    }


}