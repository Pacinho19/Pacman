package pl.pacinho.pacman.main;

import pl.pacinho.pacman.config.GameProperties;
import pl.pacinho.pacman.view.Board;

public class Run {

    public static void main(String[] args) {
        Board b = new Board(0, GameProperties.getMaxLife());
        b.setVisible(true);
    }
}