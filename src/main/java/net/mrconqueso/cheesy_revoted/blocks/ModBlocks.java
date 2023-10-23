package net.mrconqueso.cheesy_revoted.blocks;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.blocks.custom.PenguinEggBlock;

public class ModBlocks {

    public static final Block PENGUIN_EGG = registerBlock("penguin_egg",
            new PenguinEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {

        return Registry.register(Registries.ITEM, new Identifier(CheesyRevoted.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registertModBlocks() {
        CheesyRevoted.LOGGER.debug("Registering Mod Blocks for " + CheesyRevoted.MOD_ID);

    }
}