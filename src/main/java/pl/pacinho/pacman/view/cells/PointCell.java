package pl.pacinho.pacman.view.cells;

import lombok.Setter;
import pl.pacinho.pacman.logic.Images;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.model.PlayerDirection;

import javax.swing.*;
import java.awt.*;

public class PointCell extends Cell{

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
        int x = (getHeight() - getHeight()/4) / 2;
        int y = (getWidth() - getHeight()/4) / 2;
        g.setColor(Color.PINK);
        g.fillOval(x,y,getHeight()/4, getWidth()/4);
    }


}