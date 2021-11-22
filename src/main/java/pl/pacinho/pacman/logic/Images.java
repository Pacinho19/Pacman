package pl.pacinho.pacman.logic;

import pl.pacinho.pacman.model.PlayerDirection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Images {
    private static ImageIcon playerRight = loadGIF("Right");
    private static ImageIcon playerLeft = loadGIF("Left");
    private static ImageIcon playerUp = loadGIF("Up");
    private static ImageIcon playerDown = loadGIF("Down");
    private static ImageIcon playerNone = loadGIF("None");

    private static ImageIcon loadGIF(String name){
        URL url = Images.class.getClassLoader().getResource("img/" + name+".gif");
        ImageIcon imageIcon = new ImageIcon(url);
        return imageIcon;
    }

//    private static BufferedImage playerRight = load("Right.png");
//    private static BufferedImage playerLeft = load("Left.png");
//    private static BufferedImage playerUp = load("Up.png");
//    private static BufferedImage playerDown = load("Down.png");
//    private static BufferedImage playerNone = load("None.png");

    private static BufferedImage load(String name) {
        try {
            return ImageIO.read(Images.class.getClassLoader().getResource("img/" + name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ImageIcon getByDirection(PlayerDirection direction) {
        switch (direction) {
            case RIGHT:
                return playerRight;
            case LEFT:
                return playerLeft;
            case UP:
                return playerUp;
            case DOWN:
                return playerDown;
            case NONE:
                return playerNone;
        }
        return null;
    }
}