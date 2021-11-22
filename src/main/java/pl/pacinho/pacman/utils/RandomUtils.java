package pl.pacinho.pacman.utils;

import pl.pacinho.pacman.model.MonsterDirection;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    public static int getInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static MonsterDirection getMonsterDirection() {
        return Arrays.asList(MonsterDirection.values())
                .get(getInt(1, MonsterDirection.values().length));
    }
}