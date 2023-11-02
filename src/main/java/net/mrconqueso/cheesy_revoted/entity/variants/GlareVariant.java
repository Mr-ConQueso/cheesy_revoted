package net.mrconqueso.cheesy_revoted.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum GlareVariant {
    DEFAULT(0),
    Is_INVISIBLE(1),
    FLOWERING(2);

    private static final GlareVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(GlareVariant::getId)).toArray(GlareVariant[]::new);
    private final int id;

    public int getId() {
        return this.id;
    }

    GlareVariant(int id) {
        this.id = id;
    }

    public static GlareVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
