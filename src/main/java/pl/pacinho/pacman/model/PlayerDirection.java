package pl.pacinho.pacman.model;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public enum PlayerDirection {

    NONE(-1),
    UP(KeyEvent.VK_UP),
    DOWN(KeyEvent.VK_DOWN),
    LEFT(KeyEvent.VK_LEFT),
    RIGHT(KeyEvent.VK_RIGHT);

    private int keyEvent;

    PlayerDirection(int keyEvent) {
        this.keyEvent = keyEvent;
    }

    public static PlayerDirection findByKey(KeyEvent e) {
        return Arrays.asList(values())
                .stream()
                .filter(d -> d.keyEvent == e.getKeyCode())
                .findFirst()
                .orElse(NONE);
    }

}