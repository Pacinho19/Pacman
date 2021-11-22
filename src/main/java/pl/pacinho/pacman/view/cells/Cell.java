package pl.pacinho.pacman.view.cells;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.pacman.model.CellType;

import javax.swing.*;

public abstract class Cell extends JPanel {

    @Getter
    CellType cellType;

    @Getter
    @Setter
    int idx;
}