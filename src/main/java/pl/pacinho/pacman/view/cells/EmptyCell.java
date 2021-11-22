package pl.pacinho.pacman.view.cells;

import pl.pacinho.pacman.model.CellType;

import java.awt.*;

public class EmptyCell extends Cell{

    public EmptyCell(int idx) {
        this.idx=idx;
        this.cellType= CellType.EMPTY;
        init();
    }

    private void init(){
        this.setBackground(Color.WHITE);
    }
}