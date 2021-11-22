package pl.pacinho.pacman.model;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public enum Direction {

    NONE(-1),
    UP(KeyEvent.VK_UP),
    DOWN(KeyEvent.VK_DOWN),
    LEFT(KeyEvent.VK_LEFT),
    RIGHT(KeyEvent.VK_RIGHT);


    private int keyEvent;

    Direction(int keyEvent) {
        this.keyEvent = keyEvent;
    }

    public static Direction findByKey(KeyEvent e) {
        return Arrays.asList(values())
                .stream()
                .filter(d -> d.keyEvent == e.getKeyCode())
                .findFirst()
                .orElse(NONE);
    }
}