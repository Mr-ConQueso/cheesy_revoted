package net.mrconqueso.cheesy_revoted.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;
import net.mrconqueso.cheesy_revoted.item.custom.CrabClawItem;
import net.mrconqueso.cheesy_revoted.item.custom_materials.CrabClawMaterial;
import net.mrconqueso.cheesy_revoted.sound.ModSounds;

public class ModItems {

    public static final Item CRAB_SPAWN_EGG = registerItem("crab_spawn_egg",
            new SpawnEggItem(ModEntities.CRAB, 0x3858b9, 0xff9e4a, new FabricItemSettings()));
    public static final Item ARMADILLO_SPAWN_EGG = registerItem("armadillo_spawn_egg",
            new SpawnEggItem(ModEntities.ARMADILLO, 0x6a332b, 0xbb7444, new FabricItemSettings()));
    public static final Item PENGUIN_SPAWN_EGG = registerItem("penguin_spawn_egg",
            new SpawnEggItem(ModEntities.PENGUIN, 0x363b4f, 0xdbdac6, new FabricItemSettings()));

    public static final Item CRAB_CLAW = registerItem("crab_claw",
            new CrabClawItem(CrabClawMaterial.CRAB_CLAW_MATERIAL, new FabricItemSettings().maxCount(1)));
    public static final Item ARMADILLO_SCUTE = registerItem("armadillo_scute",
            new Item(new FabricItemSettings()));

    public static final Item CRAB_RAVE_MUSIC_DISC = registerItem("crab_rave_music_disc",
            new MusicDiscItem(6, ModSounds.CRAB_RAVE, new FabricItemSettings().maxCount(1), 132));


    private static void addItemsToItemGroup() {
        addToItemGroup(ItemGroups.INGREDIENTS, ARMADILLO_SCUTE);

        addToItemGroup(ItemGroups.SPAWN_EGGS, CRAB_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, ARMADILLO_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, PENGUIN_SPAWN_EGG);

        addToItemGroup(ItemGroups.TOOLS, CRAB_CLAW);
        addToItemGroup(ItemGroups.TOOLS, CRAB_RAVE_MUSIC_DISC);
    }

    private static void addToItemGroup(RegistryKey<ItemGroup> itemGroup, Item item) {
        ItemGroupEvents.modifyEntriesEvent(itemGroup).register(entries -> entries.add(item));
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(CheesyRevoted.MOD_ID, name), item);
    }

    public static void registerModItems() {
        CheesyRevoted.LOGGER.info("Registering Mod Items For " + CheesyRevoted.MOD_ID);

        addItemsToItemGroup();
    }
}
