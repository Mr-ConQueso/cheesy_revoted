package net.mrconqueso.cheesy_revoted.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.*;

public class ModEntities {

    // --------- / MOB VOTE 2023 / --------- //
    public static final EntityType<CrabEntity> CRAB = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "crab"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CrabEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.563f)).build());

    public static final EntityType<ArmadilloEntityRevoted> ARMADILLO = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "armadillo"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ArmadilloEntityRevoted::new)
                    .dimensions(EntityDimensions.fixed(0.7f, 0.65f)).build());

    public static final EntityType<PenguinEntity> PENGUIN = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "penguin"),
            FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, PenguinEntity::new)
                    .dimensions(EntityDimensions.fixed(0.8125f, 1.25f)).build());

    // --------- / MOB VOTE 2022 / --------- //

    public static final EntityType<SnifferEntityRevoted> SNIFFER = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "sniffer"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, SnifferEntityRevoted::new)
                    .dimensions(EntityDimensions.fixed(1.9f, 1.75f)).build());

    public static final EntityType<TuffGolemEntity> TUFF_GOLEM = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "tuff_golem"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, TuffGolemEntity::new)
                    .dimensions(EntityDimensions.fixed(0.875f, 1.3125f)).build());

    public static final EntityType<RascalEntity> RASCAL = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "rascal"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, RascalEntity::new)
                    .dimensions(EntityDimensions.fixed(0.875f, 1.3125f)).build());

    // --------- / MOB VOTE 2021 / --------- //
    public static final EntityType<CopperGolemEntity> COPPER_GOLEM = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "copper_golem"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, CopperGolemEntity::new)
                    .dimensions(EntityDimensions.fixed(0.875f, 1.3125f)).build());

    // --------- / MOB VOTE 2020 / --------- //
    public static final EntityType<MoobloomEntity> MOOBLOOM = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "moobloom"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoobloomEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f, 1.4f)).build());

    // --------- / BIOME CHOOSER 2019 / --------- //
    // --------- / BIOME CHOOSER 2018 / --------- //
    // --------- / MOB VOTE 2017 / --------- //
}
