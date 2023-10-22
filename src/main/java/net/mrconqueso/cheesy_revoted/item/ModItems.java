package net.mrconqueso.cheesy_revoted.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.blocks.ModBlocks;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;
import net.mrconqueso.cheesy_revoted.item.custom.CrabClawItem;
import net.mrconqueso.cheesy_revoted.item.custom_materials.CrabClawMaterial;
import net.mrconqueso.cheesy_revoted.sound.ModSounds;

import java.util.List;

public class ModItems {

    // --------- / SPAWN EGGS / --------- //
    public static final Item CRAB_SPAWN_EGG = registerItem("crab_spawn_egg",
            new SpawnEggItem(ModEntities.CRAB, 0x3858b9, 0xff9e4a, new FabricItemSettings()));
    public static final Item ARMADILLO_SPAWN_EGG = registerItem("armadillo_spawn_egg",
            new SpawnEggItem(ModEntities.ARMADILLO, 0x6a332b, 0xbb7444, new FabricItemSettings()));
    public static final Item PENGUIN_SPAWN_EGG = registerItem("penguin_spawn_egg",
            new SpawnEggItem(ModEntities.PENGUIN, 0x363b4f, 0xdbdac6, new FabricItemSettings()));
    public static final Item MOOBLOOM_SPAWN_EGG = registerItem("moobloom_spawn_egg",
            new SpawnEggItem(ModEntities.MOOBLOOM, 0xefc214, 0xfafafa, new FabricItemSettings()));

    // --------- / TOOLS & UTILITIES / --------- //
    public static final Item CRAB_CLAW = registerItem("crab_claw",
            new CrabClawItem(CrabClawMaterial.CRAB_CLAW_MATERIAL, new FabricItemSettings().maxCount(1)));

    // --------- / INGREDIENTS / --------- //
    public static final Item ARMADILLO_SCUTE = registerItem("armadillo_scute",
            new Item(new FabricItemSettings()));

    // --------- / MUSIC DISCS / --------- //
    public static final Item MUSIC_DISC_CRAB_RAVE = registerItem("music_disc_crab_rave",
            new MusicDiscItem(6, ModSounds.CRAB_RAVE, new FabricItemSettings().maxCount(1), 132));

    // --------- / CREATIVE ITEM GROUPS / --------- //
    private static void addItemsToItemGroup() {

        addToItemGroup(ItemGroups.INGREDIENTS, ARMADILLO_SCUTE, Items.SCUTE);

        addToItemGroup(ItemGroups.NATURAL, Item.fromBlock(ModBlocks.PENGUIN_EGG), Items.SNIFFER_EGG);

        addToItemGroup(ItemGroups.SPAWN_EGGS, CRAB_SPAWN_EGG, Items.COW_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, ARMADILLO_SPAWN_EGG, Items.ALLAY_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, PENGUIN_SPAWN_EGG, Items.PARROT_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, MOOBLOOM_SPAWN_EGG, Items.MAGMA_CUBE_SPAWN_EGG);

        addToItemGroup(ItemGroups.TOOLS, CRAB_CLAW, Items.SHEARS);
        addToItemGroup(ItemGroups.TOOLS, MUSIC_DISC_CRAB_RAVE, Items.MUSIC_DISC_PIGSTEP);
    }

    private static void addToItemGroup(RegistryKey<ItemGroup> itemGroup, Item item, Item itemBefore) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> {
            entries.addAfter(itemBefore, item);
        });
    }

    // --------- / REGISTER ITEMS / --------- //
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(CheesyRevoted.MOD_ID, name), item);
    }

    public static void registerModItems() {
        CheesyRevoted.LOGGER.info("Registering Mod Items For " + CheesyRevoted.MOD_ID);

        addItemsToItemGroup();
    }
}
