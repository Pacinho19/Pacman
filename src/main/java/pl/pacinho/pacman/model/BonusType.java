package pl.pacinho.pacman.model;

import lombok.Getter;

import java.util.Arrays;

public enum BonusType {

    MONSTER_FREEZE(0),
    MONSTER_KILL(1);

    @Getter
    private int id;

    BonusType(int i) {
        this.id=i;
    }

    public static BonusType findById(int iId){
        return Arrays.stream(values())
                .filter(ct -> ct.getId()==(iId))
                .findFirst()
                .orElse(MONSTER_FREEZE);
    }
}