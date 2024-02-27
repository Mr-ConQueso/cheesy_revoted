package net.mrconqueso.cheesy_revoted.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.blocks.PenguinEggBlock;
import net.mrconqueso.cheesy_revoted.blocks.SnifferEggBlockRevoted;

public class ModBlocks {

    public static final Block PENGUIN_EGG = registerBlock("penguin_egg",
            new PenguinEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)));
    public static final Block SNIFFER_EGG = registerBlock("sniffer_egg",
            new SnifferEggBlockRevoted(FabricBlockSettings.copyOf(Blocks.SNIFFER_EGG)));

    // --------- / FLOWERS / --------- //
    public static final Block DAFFODIL = registerBlock("daffodil",
            new FlowerBlock(StatusEffects.ABSORPTION, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_DAFFODIL = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_daffodil"),
            new FlowerPotBlock(DAFFODIL, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block DAHLIA = registerBlock("dahlia",
            new FlowerBlock(StatusEffects.ABSORPTION, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_DAHLIA = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_dahlia"),
            new FlowerPotBlock(DAHLIA, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block SWAMPOOL = registerBlock("swampool",
            new FlowerBlock(StatusEffects.WATER_BREATHING, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_SWAMPOOL = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_swampool"),
            new FlowerPotBlock(SWAMPOOL, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block DUSTY_MILLER = registerBlock("dusty_miller",
            new FlowerBlock(StatusEffects.WATER_BREATHING, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_DUSTY_MILLER = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_dusty_miller"),
            new FlowerPotBlock(DUSTY_MILLER, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block BLACK_SPOT = registerBlock("black_spot",
            new FlowerBlock(StatusEffects.POISON, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_BLACK_SPOT = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_black_spot"),
            new FlowerPotBlock(BLACK_SPOT, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block AMBER_ANGEL = registerBlock("amber_angel",
            new FlowerBlock(StatusEffects.POISON, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_AMBER_ANGEL = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_amber_angel"),
            new FlowerPotBlock(AMBER_ANGEL, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block AMARYLLIS = registerBlock("amaryllis",
            new FlowerBlock(StatusEffects.POISON, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_AMARYLLIS = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_amaryllis"),
            new FlowerPotBlock(AMARYLLIS, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block SPORELOOM = registerBlock("sporeloom",
            new FlowerBlock(StatusEffects.NIGHT_VISION, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_SPORELOOM = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_sporeloom"),
            new FlowerPotBlock(SPORELOOM, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block LIME_SPURGE = registerBlock("lime_spurge",
            new FlowerBlock(StatusEffects.NIGHT_VISION, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_LIME_SPURGE = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_lime_spurge"),
            new FlowerPotBlock(LIME_SPURGE, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block OSTRICH_FERN = registerBlock("ostrich_fern",
            new FlowerBlock(StatusEffects.NIGHT_VISION, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_OSTRICH_FERN = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_ostrich_fern"),
            new FlowerPotBlock(OSTRICH_FERN, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block LUMISHROOM = registerBlock("lumishroom",
            new FlowerBlock(StatusEffects.GLOWING, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_LUMISHROOM = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_lumishroom"),
            new FlowerPotBlock(LUMISHROOM, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block SOUL_FLOWERS = registerBlock("soul_flowers",
            new FlowerBlock(StatusEffects.NIGHT_VISION, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block BLUE_IRIS = registerBlock("blue_iris",
            new FlowerBlock(StatusEffects.NIGHT_VISION, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_BLUE_IRIS = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_blue_iris"),
            new FlowerPotBlock(BLUE_IRIS, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block POTTED_SOUL_FLOWERS = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_soul_flowers"),
            new FlowerPotBlock(SOUL_FLOWERS, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block WILD_SMELLER = registerBlock("wild_smeller",
            new FlowerBlock(StatusEffects.NAUSEA, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_WILD_SMELLER = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_wild_smeller"),
            new FlowerPotBlock(WILD_SMELLER, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block HIBISCUS_ROSE = registerBlock("hibiscus_rose",
            new FlowerBlock(StatusEffects.REGENERATION, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_HIBISCUS_ROSE = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_hibiscus_rose"),
            new FlowerPotBlock(HIBISCUS_ROSE, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));
    public static final Block BUTTERFLY_WEED = registerBlock("butterfly_weed",
            new FlowerBlock(StatusEffects.ABSORPTION, 10,
                    FabricBlockSettings.copyOf(Blocks.DANDELION).nonOpaque().noCollision()));
    public static final Block POTTED_BUTTERFLY_WEED = Registry.register(Registries.BLOCK, new Identifier(CheesyRevoted.MOD_ID, "potted_butterfly_weed"),
            new FlowerPotBlock(BUTTERFLY_WEED, FabricBlockSettings.copyOf(Blocks.POTTED_DANDELION).nonOpaque()));




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
