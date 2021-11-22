package pl.pacinho.pacman.view.cells;

import lombok.Getter;
import pl.pacinho.pacman.model.CellType;

import javax.swing.*;
import java.awt.*;

public class ExtraPointCell extends PointCell {

    @Getter
    private boolean defaultPoint;

    public ExtraPointCell(int idx, boolean b) {
        defaultPoint=b;
        this.idx=idx;
        this.cellType = CellType.POINT;
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
        this.setBackground(Color.GREEN);
    }


}