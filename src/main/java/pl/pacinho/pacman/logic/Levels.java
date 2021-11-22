package pl.pacinho.pacman.logic;

import javafx.util.Pair;
import lombok.Getter;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.view.cells.Cell;
import pl.pacinho.pacman.view.cells.PlayerCell;
import pl.pacinho.pacman.view.cells.PointCell;
import pl.pacinho.pacman.view.cells.WallCell;

import java.util.ArrayList;

public class Levels {

    @Getter
    private static ArrayList<String> levelsMap = new ArrayList<String>() {
        {
             add( "* * * * * * * * * * * * * * * \n" +
                            "* P @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ @ @ @ @ @ @ @ *\n" +
                            "* * * * @ * * @ * * @ * * * *\n" +
                            "* @ @ @ @ @ @ @ @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ * * *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* * * * * * * * * * * * * * *");

            add( "* * * * * * * * * * * * * * * * * * * * * * * * * * *\n" +
                            "* P @ @ @ @ @ @ @ @ @ @ @ * @ @ @ @ @ @ @ @ @ @ @ @ *\n" +
                            "* @ * * * * @ * * * * * @ * @ * * * * * @ * * * * @ *\n" +
                            "* @ * * * * @ * * * * * @ * @ * * * * * @ * * * * @ *\n" +
                            "* @ * * * * @ * * * * * @ * @ * * * * * @ * * * * @ *\n" +
                            "* @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ @ *\n" +
                            "* @ * * * * @ * @ * * * * * * * * * @ * @ * * * * @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ * @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* * * * * * @ * * * * * @ * @ * * * * * @ * * * * * *\n" +
                            "* * * * * * @ * @ @ @ @ @ @ @ @ @ @ @ * @ * * * * * *\n" +
                            "* * * * * * @ * @ * * * * * * * * * @ * @ * * * * * *\n" +
                            "* * * * * * * * * * * * * * * * * * * * * * * * * * *");

        }
    };

    public static Pair<Integer, Integer> getDimension(String levelMap) {
        String[] split = levelMap.split("\n");
        int rows = split.length;
        int cols = split[0].split(" ").length;
        return new Pair<>(rows, cols);
    }

    public static Cell getCellInstance(CellType cellType) {
        switch (cellType) {
            case WALL:
                return new WallCell();
            case PLAYER:
                return new PlayerCell();
            case POINT:
                return new PointCell();
        }
        return null;
    }
}