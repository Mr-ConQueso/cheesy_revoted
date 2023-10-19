package net.mrconqueso.cheesy_revoted.entity.variants;

import java.util.Arrays;
import java.util.Comparator;

public enum MoobloomVariant {
    DEFAULT(0),
    WHITE(1),
    LIGHT_GRAY(2),
    GRAY(3),
    BLACK(4),
    BROWN(5),
    RED(6),
    ORANGE(7),
    LIME(8),
    GREEN(9),
    CYAN(10),
    LIGHT_BLUE(11),
    BLUE(12),
    PURPLE(13),
    MAGENTA(14),
    PINK(15);

    private static final MoobloomVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(MoobloomVariant::getId)).toArray(MoobloomVariant[]::new);
    private final int id;

    public int getId() {
        return this.id;
    }

    MoobloomVariant(int id) {
        this.id = id;
    }

    public static MoobloomVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
