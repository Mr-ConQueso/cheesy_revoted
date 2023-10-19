package net.mrconqueso.cheesy_revoted.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;

public class ModEntityGeneration {
    public static void addSpawns() {

        // --------- / SPAWN ENTITIES IN WORLD / --------- //

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA), SpawnGroup.CREATURE,
                ModEntities.ARMADILLO, 35, 1, 2);

        SpawnRestriction.register(ModEntities.ARMADILLO, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);


        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.MANGROVE_SWAMP, BiomeKeys.BEACH), SpawnGroup.CREATURE,
                ModEntities.CRAB, 35, 1, 2);

        SpawnRestriction.register(ModEntities.CRAB, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);


        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.STONY_SHORE, BiomeKeys.FROZEN_OCEAN), SpawnGroup.CREATURE,
                ModEntities.PENGUIN, 35, 1, 2);

        SpawnRestriction.register(ModEntities.PENGUIN, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
    }

}
