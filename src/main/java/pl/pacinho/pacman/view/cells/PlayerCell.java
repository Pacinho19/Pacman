package pl.pacinho.pacman.view.cells;

import lombok.Getter;
import pl.pacinho.pacman.logic.Images;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.model.PlayerDirection;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

@Getter
public class PlayerCell extends Cell {

    private PlayerDirection direction;
    private ImageIcon image;
private MatteBorder border;

    public PlayerCell() {
        this.cellType = CellType.PLAYER;
        setDirection(PlayerDirection.NONE);
        this.setDoubleBuffered(true);
        init();
    }

    private void init() {
        border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE);
        this.setBorder(border);
        this.setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        g.drawImage(image.getImage(),
                (int) (d.width*0.1),
                (int)(d.height*0.1),
                (int) (d.width-(d.width*0.2)),
                (int) (d.height-(d.height*0.2)),
                this);
    }

    public void setDirection(PlayerDirection direction) {
        this.direction = direction;
        image = Images.getByDirection(direction);
    }


    public void setMonsterKillBonusBorder(){
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        this.repaint();
    }

    public void setOriginalBorder(){
        this.setBorder(border);
        this.repaint();
    }

}