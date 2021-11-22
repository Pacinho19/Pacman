package pl.pacinho.pacman.model;


import lombok.Getter;

import java.util.Arrays;

public enum CellType {

    WALL("*"),
    PLAYER("P"),
    MONSTER("M"),
    POINT("@"),
    FINISH("#"),
    EMPTY("_");

    @Getter
    private String symbol;

    CellType(String symbol) {
        this.symbol = symbol;
    }

    public static CellType findBySymbol(String symbol){
        return Arrays.stream(values())
                .filter(ct -> ct.getSymbol().equals(symbol))
                .findFirst()
                .orElse(EMPTY);
    }
}