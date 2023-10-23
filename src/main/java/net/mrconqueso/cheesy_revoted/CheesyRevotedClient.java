package net.mrconqueso.cheesy_revoted;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.client.WolfArmorModel;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;
import net.mrconqueso.cheesy_revoted.entity.client.ArmadilloRenderer;
import net.mrconqueso.cheesy_revoted.entity.client.CrabRenderer;
import net.mrconqueso.cheesy_revoted.entity.client.MoobloomRenderer;
import net.mrconqueso.cheesy_revoted.entity.client.PenguinRenderer;

public class CheesyRevotedClient implements ClientModInitializer {
    public static final EntityModelLayer WOLF_ARMOR = new EntityModelLayer(new Identifier(CheesyRevoted.MOD_ID,"wolf_armor"), "wolf_armor");

    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(WOLF_ARMOR, () -> WolfArmorModel.getTexturedModelData(new Dilation(0.35f)));

        // --------- / GECKOLIB ENTITY-RENDERING / --------- //

        EntityRendererRegistry.register(ModEntities.CRAB, CrabRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARMADILLO, ArmadilloRenderer::new);
        EntityRendererRegistry.register(ModEntities.PENGUIN, PenguinRenderer::new);
        EntityRendererRegistry.register(ModEntities.MOOBLOOM, MoobloomRenderer::new);

    }
}
