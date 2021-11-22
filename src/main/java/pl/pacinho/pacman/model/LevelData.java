package pl.pacinho.pacman.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LevelData {

    private String board;
    private int monsterCount;
}