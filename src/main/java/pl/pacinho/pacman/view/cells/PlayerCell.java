package pl.pacinho.pacman.view.cells;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.pacman.logic.Images;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.model.PlayerDirection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

@Getter
public class PlayerCell extends Cell {

    private PlayerDirection direction;
    private ImageIcon image;

    public PlayerCell() {
        this.cellType = CellType.PLAYER;
        setDirection(PlayerDirection.NONE);
        init();
    }

    private void init() {
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
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

}