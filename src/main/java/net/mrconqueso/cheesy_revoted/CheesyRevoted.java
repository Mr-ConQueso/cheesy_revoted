package net.mrconqueso.cheesy_revoted;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.mrconqueso.cheesy_revoted.blocks.ModBlocks;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;
import net.mrconqueso.cheesy_revoted.entity.custom.ArmadilloEntity;
import net.mrconqueso.cheesy_revoted.entity.custom.CrabEntity;
import net.mrconqueso.cheesy_revoted.entity.custom.MoobloomEntity;
import net.mrconqueso.cheesy_revoted.entity.custom.PenguinEntity;
import net.mrconqueso.cheesy_revoted.item.ModItems;
import net.mrconqueso.cheesy_revoted.sound.ModSounds;
import net.mrconqueso.cheesy_revoted.world.gen.ModEntityGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheesyRevoted implements ModInitializer {

	public static final String MOD_ID = "cheesy_revoted";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Cheesy's Revoted Ideas is Initializing!");

		// --------- / INITIALIZE FEATURE CLASSES / --------- //

		ModItems.registerModItems();
		ModBlocks.registertModBlocks();
		ModSounds.registerSounds();
		ModEntityGeneration.addSpawns();

		// --------- / REGISTER ENTITIES / --------- //

		FabricDefaultAttributeRegistry.register(ModEntities.CRAB, CrabEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ARMADILLO, ArmadilloEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.PENGUIN, PenguinEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MOOBLOOM, MoobloomEntity.setAttributes());

	}
}