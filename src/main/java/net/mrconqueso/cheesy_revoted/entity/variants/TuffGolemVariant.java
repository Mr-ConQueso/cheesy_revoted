package net.mrconqueso.cheesy_revoted.entity.variants;

import net.minecraft.block.Block;
import net.mrconqueso.cheesy_revoted.registry.ModBlocks;

import java.util.Arrays;
import java.util.Comparator;

public enum TuffGolemVariant {
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

    private static final TuffGolemVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(TuffGolemVariant::getId)).toArray(TuffGolemVariant[]::new);
    private final int id;

    public int getId() {
        return this.id;
    }

    TuffGolemVariant(int id) {
        this.id = id;
    }

    public static TuffGolemVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }

    /*
    public static Block getCarpetDrop(int id) {
        switch (id) {
            case 0:
                return ModBlocks.DAFFODIL;
            case 1:
                return ModBlocks.DAHLIA;
            case 2:
                return ModBlocks.DUSTY_MILLER;
            case 3:
                return ModBlocks.SWAMPOOL;
            case 4:
                return ModBlocks.BLACK_SPOT;
            case 5:
                return ModBlocks.AMBER_ANGEL;
            case 6:
                return ModBlocks.DAHLIA;
            case 7:
                return ModBlocks.AMARYLLIS;
            case 8:
                return ModBlocks.LIME_SPURGE;
            case 9:
                return ModBlocks.OSTRICH_FERN;
            case 10:
                return ModBlocks.BUTTERFLY_WEED;
            case 11:
                return ModBlocks.LUMISHROOM;
            case 12:
                return ModBlocks.BLUE_IRIS;
            case 13:
                return ModBlocks.HIBISCUS_ROSE;
            case 14:
                return ModBlocks.BUTTERFLY_WEED;
            case 15:
                return ModBlocks.DAHLIA;
            default:
                return ModBlocks.DAFFODIL;
        }
    }
     */
}
