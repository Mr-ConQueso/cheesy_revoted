package net.mrconqueso.cheesy_revoted;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.passive.WolfEntity;
import net.mrconqueso.cheesy_revoted.config.ModConfig;
import net.mrconqueso.cheesy_revoted.entity.*;
import net.mrconqueso.cheesy_revoted.registry.*;
import net.mrconqueso.cheesy_revoted.implementation.WolfDataAccessor;
import net.mrconqueso.cheesy_revoted.implementation.WolfInteractionHandler;
import net.mrconqueso.cheesy_revoted.world.gen.ModEntityGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheesyRevoted implements ModInitializer {

	public static final String MOD_ID = "cheesy_revoted";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static WolfDataAccessor getData(WolfEntity wolf) {
		return ((WolfDataAccessor) wolf);
	}

	@Override
	public void onInitialize() {

		LOGGER.info("Cheesy's Revoted Ideas is Initializing!");

		// --------- / INITIALIZE CONFIG / --------- //

		//AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

		// --------- / INITIALIZE FEATURE CLASSES / --------- //

		ModItems.registerModItems();
		ModBlocks.registertModBlocks();
		ModItemGroup.registerModItemGroup();
		ModSounds.registerSounds();
		ModEntityGeneration.addSpawns();

		// --------- / WOLF ARMOR / --------- //

		UseEntityCallback.EVENT.register(new WolfInteractionHandler());

		// --------- / REGISTER ENTITIES / --------- //

		registerEntities();

	}

	private void registerEntities() {

		// --------- / MOB VOTE 2023 / --------- //

		FabricDefaultAttributeRegistry.register(ModEntities.CRAB, CrabEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ARMADILLO, ArmadilloEntityRevoted.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.PENGUIN, PenguinEntity.setAttributes());

		// --------- / MOB VOTE 2022 / --------- //
		FabricDefaultAttributeRegistry.register(ModEntities.SNIFFER, SnifferEntityRevoted.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.TUFF_GOLEM, TuffGolemEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.RASCAL, RascalEntity.setAttributes());


		// --------- / MOB VOTE 2021 / --------- //

		FabricDefaultAttributeRegistry.register(ModEntities.COPPER_GOLEM, CopperGolemEntity.setAttributes());

		// --------- / MOB VOTE 2020 / --------- //

		FabricDefaultAttributeRegistry.register(ModEntities.MOOBLOOM, MoobloomEntity.setAttributes());

		// --------- / BIOME CHOOSER 2019 / --------- //



		// --------- / BIOME CHOOSER 2018 / --------- //



		// --------- / MOB VOTE 2017 / --------- //



	}
}