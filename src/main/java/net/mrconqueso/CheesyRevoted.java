package net.mrconqueso;

import net.mrconqueso.entity.ModEntities;
import net.mrconqueso.entity.custom.ArmadilloEntity;
import net.mrconqueso.entity.custom.CrabEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.mrconqueso.entity.custom.PenguinEntity;
import net.mrconqueso.item.ModItems;
import net.mrconqueso.sound.ModSounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheesyRevoted implements ModInitializer {

	public static final String MOD_ID = "cheesy_revoted";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Cheesy's Revoted Ideas is Initializing!");

		ModItems.registerModItems();
		ModSounds.registerSounds();

		FabricDefaultAttributeRegistry.register(ModEntities.CRAB, CrabEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ARMADILLO, ArmadilloEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.PENGUIN, PenguinEntity.setAttributes());

	}
}