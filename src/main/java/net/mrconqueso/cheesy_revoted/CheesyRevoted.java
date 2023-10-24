package net.mrconqueso.cheesy_revoted;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.passive.WolfEntity;
import net.mrconqueso.cheesy_revoted.config.ModConfig;
import net.mrconqueso.cheesy_revoted.registry.ModBlocks;
import net.mrconqueso.cheesy_revoted.registry.ModEntities;
import net.mrconqueso.cheesy_revoted.entity.ArmadilloEntity;
import net.mrconqueso.cheesy_revoted.entity.CrabEntity;
import net.mrconqueso.cheesy_revoted.entity.MoobloomEntity;
import net.mrconqueso.cheesy_revoted.entity.PenguinEntity;
import net.mrconqueso.cheesy_revoted.implementation.WolfDataAccessor;
import net.mrconqueso.cheesy_revoted.implementation.WolfInteractionHandler;
import net.mrconqueso.cheesy_revoted.registry.ModItems;
import net.mrconqueso.cheesy_revoted.registry.ModSounds;
import net.mrconqueso.cheesy_revoted.world.gen.ModEntityGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheesyRevoted implements ModInitializer {

	public static final String MOD_ID = "cheesy_revoted";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Cheesy's Revoted Ideas is Initializing!");

		// --------- / INITIALIZE CONFIG / --------- //

		//AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
		AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);

		// --------- / INITIALIZE FEATURE CLASSES / --------- //

		ModItems.registerModItems();
		ModBlocks.registertModBlocks();
		ModSounds.registerSounds();
		ModEntityGeneration.addSpawns();

		// --------- / WOLF ARMOR / --------- //

		UseEntityCallback.EVENT.register(new WolfInteractionHandler());

		// --------- / REGISTER ENTITIES / --------- //

		FabricDefaultAttributeRegistry.register(ModEntities.CRAB, CrabEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.ARMADILLO, ArmadilloEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.PENGUIN, PenguinEntity.setAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.MOOBLOOM, MoobloomEntity.setAttributes());

	}

	public static WolfDataAccessor getData(WolfEntity wolf) {
		return ((WolfDataAccessor) wolf);
	}
}