package pl.pacinho.pacman.view.cells;

import pl.pacinho.pacman.model.CellType;

import javax.swing.*;
import java.awt.*;

public class PointCell extends Cell {

    public PointCell() {
        this.cellType = CellType.POINT;
        this.setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBorder(BorderFactory.createMatteBorder(
                (int) (this.getHeight() * 0.35),
                (int) (this.getWidth() * 0.35),
                (int) (this.getHeight() * 0.35),
                (int) (this.getWidth() * 0.35),
                Color.WHITE));
        this.setBackground(Color.PINK);

    }


}