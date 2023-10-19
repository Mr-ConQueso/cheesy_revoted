package net.mrconqueso.cheesy_revoted.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.custom.ArmadilloEntity;
import net.mrconqueso.cheesy_revoted.entity.custom.CrabEntity;
import net.mrconqueso.cheesy_revoted.entity.custom.MoobloomEntity;
import net.mrconqueso.cheesy_revoted.entity.custom.PenguinEntity;

public class ModEntities {

    public static final EntityType<CrabEntity> CRAB = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "crab"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, CrabEntity::new)
                    .dimensions(EntityDimensions.fixed(0.75f, 0.563f)).build());

    public static final EntityType<ArmadilloEntity> ARMADILLO = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "armadillo"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, ArmadilloEntity::new)
                    .dimensions(EntityDimensions.fixed(1.125f, 1.0f)).build());

    public static final EntityType<PenguinEntity> PENGUIN = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "penguin"),
            FabricEntityTypeBuilder.create(SpawnGroup.WATER_CREATURE, PenguinEntity::new)
                    .dimensions(EntityDimensions.fixed(0.8125f, 1.25f)).build());

    public static final EntityType<MoobloomEntity> MOOBLOOM = Registry.register(
            Registries.ENTITY_TYPE, new Identifier(CheesyRevoted.MOD_ID, "moobloom"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MoobloomEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9f, 1.4f)).build());
}
