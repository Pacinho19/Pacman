package pl.pacinho.pacman.logic;

import javafx.util.Pair;
import lombok.Getter;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.view.cells.*;

import java.util.ArrayList;

public class Levels {

    @Getter
    private static ArrayList<String> levelsMap = new ArrayList<String>() {
        {
            add(
                    "* * * * * * * * * * * * * * * \n" +
                            "* P @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* * * @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ @ @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ @ @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ * * *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* @ @ @ @ @ @ * @ @ @ @ @ @ *\n" +
                            "* * * * * * * * * * * * * * *");
//                    "* * * * * * * * * * * * * * * \n" +
//                            "* P _ _ _ _ @ * _ _ _ _ _ _ *\n" +
//                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
//                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
//                            "* * * _ _ _ _ * _ _ _ _ _ _ *\n" +
//                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
//                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
//                            "* _ _ _ _ _ _ _ _ _ _ _ _ _ *\n" +
//                            "* _ _ _ _ _ _ _ _ _ _ _ _ _ *\n" +
//                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
//                            "* _ _ _ _ _ _ * _ _ _ _ * * *\n" +
//                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
//                            "* _ _ _ _ _ _ * _ _ _ _ _ _ *\n" +
//                            "* * * * * * * * * * * * * * *"); //TEST
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
            case POINT:
                return new PointCell();
        }
        return new EmptyCell();
    }
}