package net.mrconqueso.cheesy_revoted.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> PENGUIN_LAY_EGG_ON =
                createTag("penguin_lay_egg_on");
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(CheesyRevoted.MOD_ID, name));
        }
    }

    public static class Items {
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM , new Identifier(CheesyRevoted.MOD_ID, name));
        }
    }
}
