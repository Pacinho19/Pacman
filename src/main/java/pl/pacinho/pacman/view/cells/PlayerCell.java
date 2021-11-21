package pl.pacinho.pacman.view.cells;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.pacman.model.CellType;
import pl.pacinho.pacman.model.Direction;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class PlayerCell extends Cell {

    private Direction direction;
    private int position;

    public PlayerCell() {
        this.direction=Direction.NONE;
        this.cellType = CellType.PLAYER;
        init();
    }

    private void init() {
        this.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.WHITE));
        this.setBackground(Color.YELLOW);
    }

}