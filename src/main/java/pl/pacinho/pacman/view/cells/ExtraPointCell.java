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
        init();
    }

    private void init() {
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int oHeight = (int) (getHeight() * 0.2);
        int oWidth = (int) (getWidth() * 0.2);
        int x = (getHeight() - oHeight) / 2;
        int y = (getWidth() - oWidth) / 2;
        g.setColor(Color.green);
        g.fillOval(x, y, oHeight, oWidth);
    }


}