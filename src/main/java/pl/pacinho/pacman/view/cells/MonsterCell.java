package pl.pacinho.pacman.view.cells;

import lombok.Setter;
import pl.pacinho.pacman.logic.Images;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.model.PlayerDirection;

import javax.swing.*;
import java.awt.*;

public class MonsterCell extends Cell{

    @Setter
    private ImageIcon image;

    public MonsterCell() {
        this.cellType = CellType.MONSTER;
        this.image = Images.getMonster();
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


}