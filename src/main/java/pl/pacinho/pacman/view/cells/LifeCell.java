package pl.pacinho.pacman.view.cells;

import lombok.Getter;
import pl.pacinho.pacman.logic.Images;
import pl.pacinho.pacman.model.CellType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LifeCell extends JPanel {

    private BufferedImage image;

    public LifeCell() {
        image = Images.getLifeImage();
        this.setDoubleBuffered(true);
        JLabel lifeIcon = new JLabel();
        Image scaledInstance = image.getScaledInstance(40, 40,
                Image.SCALE_SMOOTH);
        lifeIcon.setIcon(new ImageIcon(scaledInstance));
        this.add(lifeIcon);
    }

}