package net.mrconqueso.cheesy_revoted.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum PenguinVariant {
    DEFAULT(0),
    BABY(1);

    private static final PenguinVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(PenguinVariant::getId)).toArray(PenguinVariant[]::new);
    private final int id;

    private int getId() {
        return this.id;
    }

    PenguinVariant(int id) {
        this.id = id;
    }

    public static PenguinVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
