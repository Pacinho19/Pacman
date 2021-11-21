package pl.pacinho.pacman.logic;

import javafx.util.Pair;
import lombok.Getter;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.view.cells.Cell;
import pl.pacinho.pacman.view.cells.EmptyCell;
import pl.pacinho.pacman.view.cells.PlayerCell;
import pl.pacinho.pacman.view.cells.WallCell;

import java.util.ArrayList;

public class Levels {

    @Getter
    private static ArrayList<String> levelsMap = new ArrayList<String>() {
        {
            add(
                    "* * * * * * * * * * * * * * * \n" +
                            "* P _ _ _ _ _ * _ _ _ _ _ _ *\n" +
                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
                            "* * * _ _ _ _ * _ _ _ _ _ _ *\n" +
                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
                            "* _ _ _ _ _ _ _ _ _ _ _ _ _ *\n" +
                            "* _ _ _ _ _ _ _ _ _ _ _ _ _ *\n" +
                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
                            "* _ _ _ _ _ _ * _ _ _ _ * * *\n" +
                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
                            "* * * * * * * * * * * * * * *");
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
            case EMPTY:
                return new EmptyCell();
            case PLAYER:
                return new PlayerCell();
        }
        return new EmptyCell();
    }
}