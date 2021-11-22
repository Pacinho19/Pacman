package pl.pacinho.pacman.view.cells;

import lombok.Getter;
import pl.pacinho.pacman.model.CellType;

import javax.swing.*;
import java.awt.*;

public class MonsterKillerCell extends Cell{

    @Getter
    private boolean defaultPoint;

    public MonsterKillerCell(int idx, boolean b) {
        this.defaultPoint=b;
        this.idx=idx;
        this.cellType = CellType.MONSTER_KILLER;
        this.setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBorder(BorderFactory.createMatteBorder(
                (int) (this.getHeight() * 0.30),
                (int) (this.getWidth() * 0.30),
                (int) (this.getHeight() * 0.30),
                (int) (this.getWidth() * 0.30),
                Color.WHITE));
        this.setBackground(Color.BLUE);
    }
}