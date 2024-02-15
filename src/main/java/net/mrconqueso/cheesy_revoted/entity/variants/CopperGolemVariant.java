package net.mrconqueso.cheesy_revoted.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum CopperGolemVariant {
    DEFAULT(0),
    EXPOSED(1),
    WEATHERED(2),
    OXIDIZED(3);

    private static final CopperGolemVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(CopperGolemVariant::getId)).toArray(CopperGolemVariant[]::new);
    private final int id;

    public int getId() {
        return this.id;
    }

    CopperGolemVariant(int id) {
        this.id = id;
    }

    public static CopperGolemVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
