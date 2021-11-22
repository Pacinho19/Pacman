package pl.pacinho.pacman.view.cells;

import pl.pacinho.pacman.model.CellType;

import javax.swing.*;
import java.awt.*;

public class FinishCell extends Cell{

    private ImageIcon image;

    public FinishCell() {
        this.cellType = CellType.FINISH;
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
        g.setColor(Color.RED);
        g.fillRect(x,y,getHeight()/3, getWidth()/3);
    }


}