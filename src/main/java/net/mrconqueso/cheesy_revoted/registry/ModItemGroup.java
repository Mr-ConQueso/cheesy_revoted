package net.mrconqueso.cheesy_revoted.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;

public class ModItemGroup {

    // --------- / CREATIVE ITEM GROUPS / --------- //
    private static void addItemsToItemGroup() {

        addToItemGroup(ItemGroups.INGREDIENTS, ModItems.ARMADILLO_SCUTE, Items.SCUTE);

        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.SNIFFER_EGG), Items.TURTLE_EGG);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.PENGUIN_EGG), Items.TURTLE_EGG);

        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.DAFFODIL), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.LUMISHROOM), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.WILD_SMELLER), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.SWAMPOOL), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.SPORELOOM), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.SOUL_FLOWERS), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.BLUE_IRIS), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.BLACK_SPOT), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.BUTTERFLY_WEED), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.HIBISCUS_ROSE), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.DAHLIA), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.DUSTY_MILLER), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.AMBER_ANGEL), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.AMARYLLIS), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.LIME_SPURGE), Items.WITHER_ROSE);
        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.OSTRICH_FERN), Items.WITHER_ROSE);

        addToItemGroup(ItemGroups.SPAWN_EGGS, ModItems.CRAB_SPAWN_EGG, Items.COW_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, ModItems.ARMADILLO_SPAWN_EGG, Items.ALLAY_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, ModItems.PENGUIN_SPAWN_EGG, Items.PARROT_SPAWN_EGG);

        addToItemGroup(ItemGroups.SPAWN_EGGS, ModItems.SNIFFER_SPAWN_EGG, Items.SLIME_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, ModItems.RASCAL_SPAWN_EGG, Items.RABBIT);
        addToItemGroup(ItemGroups.SPAWN_EGGS, ModItems.TUFF_GOLEM_SPAWN_EGG, Items.TROPICAL_FISH);

        addToItemGroup(ItemGroups.SPAWN_EGGS, ModItems.COPPER_GOLEM_SPAWN_EGG, Items.COD_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, ModItems.MOOBLOOM_SPAWN_EGG, Items.MAGMA_CUBE_SPAWN_EGG);

        addToItemGroup(ItemGroups.COMBAT, ModItems.WOLF_AMOR, Items.TURTLE_HELMET);

        addToItemGroup(ItemGroups.TOOLS, ModItems.CRAB_CLAW, Items.SHEARS);
        addToItemGroup(ItemGroups.TOOLS, ModItems.MUSIC_DISC_CRAB_RAVE, Items.MUSIC_DISC_PIGSTEP);
    }

    private static void addToItemGroup(RegistryKey<ItemGroup> itemGroup, Item item, Item itemBefore) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> {
            entries.addAfter(itemBefore, item);
        });
    }

    public static void registerModItemGroup() {

        CheesyRevoted.LOGGER.info("Registering Mod Item Groups For " + CheesyRevoted.MOD_ID);

        addItemsToItemGroup();
    }
}
