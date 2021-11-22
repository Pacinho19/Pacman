package pl.pacinho.pacman.model;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.pacman.view.cells.Cell;

@Getter
public class Bonus {

    @Setter
    private BonusType bonusType;

    private final int bonusDelay = 30;
    private final int bonusTime = 20;

    private int bonusTick = 0;
    private int tickWithoutBonus = 0;

    @Setter
    private boolean active;
    @Setter
    private boolean inUse;

    @Setter
    private Cell cell;

    public void incrementTickWithoutBonus() {
        tickWithoutBonus++;
    }
    public void incrementBonusTick() {
        bonusTick++;
    }

    public void clear(){
        bonusTick = 0;
        active = false;
        inUse = false;
        tickWithoutBonus = 0;
        cell = null;
    }
}