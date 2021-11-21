package pl.pacinho.pacman.view.cells;

import pl.pacinho.pacman.model.CellType;

import javax.swing.*;
import java.awt.*;

public class WallCell extends Cell {

    public WallCell() {
      this.cellType=CellType.WALL;
      init();
    }

    private void init(){
        this.setBackground(Color.GRAY);
    }
}