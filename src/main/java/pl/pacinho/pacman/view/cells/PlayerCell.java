package pl.pacinho.pacman.view.cells;

import lombok.Getter;
import pl.pacinho.pacman.logic.Images;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.model.PlayerDirection;

import javax.swing.*;
import java.awt.*;

@Getter
public class PlayerCell extends Cell {

    private PlayerDirection direction;
    private ImageIcon image;
    private boolean border;

    public PlayerCell() {
        this.cellType = CellType.PLAYER;
        setDirection(PlayerDirection.NONE);
        this.setDoubleBuffered(true);
        init();
    }

    private void init() {
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Dimension d = getSize();
        if(border) {
            g2.setStroke(new BasicStroke(3));
            g.setColor(Color.RED);
        }else{
            g.setColor(Color.WHITE);
        }

        g.fillRect(0, 0, d.width, d.height);
        g.drawImage(image.getImage(),
                (int) (d.width * 0.1),
                (int) (d.height * 0.1),
                (int) (d.width - (d.width * 0.2)),
                (int) (d.height - (d.height * 0.2)),
                this);
    }

    public void setDirection(PlayerDirection direction) {
        this.direction = direction;
        image = Images.getByDirection(direction);
    }


    public void setMonsterKillBonusBorder() {
        this.border = true;
        this.repaint();
    }

    public void setOriginalBorder() {
        this.border = false;
        this.repaint();
    }

}