package net.mrconqueso.cheesy_revoted.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.item.CrabClawItem;
import net.mrconqueso.cheesy_revoted.item.WolfArmorItem;
import net.mrconqueso.cheesy_revoted.item.custom_materials.CrabClawMaterial;

public class ModItems {

    // --------- / SPAWN EGGS / --------- //
    public static final Item CRAB_SPAWN_EGG = registerItem("crab_spawn_egg",
            new SpawnEggItem(ModEntities.CRAB, 0x3858b9, 0xff9e4a, new FabricItemSettings()));
    public static final Item ARMADILLO_SPAWN_EGG = registerItem("armadillo_spawn_egg",
            new SpawnEggItem(ModEntities.ARMADILLO, 0x93605c, 0x522d2d, new FabricItemSettings()));
    public static final Item PENGUIN_SPAWN_EGG = registerItem("penguin_spawn_egg",
            new SpawnEggItem(ModEntities.PENGUIN, 0x363b4f, 0xdbdac6, new FabricItemSettings()));

    public static final Item SNIFFER_SPAWN_EGG = registerItem("sniffer_spawn_egg",
            new SpawnEggItem(ModEntities.SNIFFER, 8855049, 2468720, new FabricItemSettings()));
    public static final Item TUFF_GOLEM_SPAWN_EGG = registerItem("tuff_golem_spawn_egg",
            new SpawnEggItem(ModEntities.TUFF_GOLEM, 0x85837b, 0x901b1f, new FabricItemSettings()));
    public static final Item RASCAL_SPAWN_EGG = registerItem("rascal_spawn_egg",
            new SpawnEggItem(ModEntities.RASCAL, 0xb0bbbb, 0x3ea095, new FabricItemSettings()));

    public static final Item COPPER_GOLEM_SPAWN_EGG = registerItem("copper_golem_spawn_egg",
            new SpawnEggItem(ModEntities.COPPER_GOLEM, 0xe3826c, 0x7a2c25, new FabricItemSettings()));

    public static final Item MOOBLOOM_SPAWN_EGG = registerItem("moobloom_spawn_egg",
            new SpawnEggItem(ModEntities.MOOBLOOM, 0xefc214, 0xfafafa, new FabricItemSettings()));

    // --------- / TOOLS & UTILITIES / --------- //
    public static final Item CRAB_CLAW = registerItem("crab_claw",
            new CrabClawItem(CrabClawMaterial.CRAB_CLAW_MATERIAL, new FabricItemSettings().maxCount(1)));
    public static final Item WOLF_AMOR = registerItem("wolf_armor",
            new WolfArmorItem(new FabricItemSettings().maxCount(1)));

    // --------- / INGREDIENTS / --------- //
    public static final Item ARMADILLO_SCUTE = registerItem("armadillo_scute",
            new Item(new FabricItemSettings()));

    // --------- / MUSIC DISCS / --------- //
    public static final Item MUSIC_DISC_CRAB_RAVE = registerItem("music_disc_crab_rave",
            new MusicDiscItem(6, ModSounds.CRAB_RAVE, new FabricItemSettings().maxCount(1), 132));


    // --------- / REGISTER ITEMS / --------- //
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(CheesyRevoted.MOD_ID, name), item);
    }

    public static void registerModItems() {
        CheesyRevoted.LOGGER.info("Registering Mod Items For " + CheesyRevoted.MOD_ID);
    }
}
