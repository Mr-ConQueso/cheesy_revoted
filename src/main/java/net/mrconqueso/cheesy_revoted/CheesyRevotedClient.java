package net.mrconqueso.cheesy_revoted;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.client.WolfArmorModel;
import net.mrconqueso.cheesy_revoted.entity.*;
import net.mrconqueso.cheesy_revoted.entity.client.*;
import net.mrconqueso.cheesy_revoted.registry.ModBlocks;
import net.mrconqueso.cheesy_revoted.registry.ModEntities;

public class CheesyRevotedClient implements ClientModInitializer {
    public static final EntityModelLayer WOLF_ARMOR = new EntityModelLayer(new Identifier(CheesyRevoted.MOD_ID,"wolf_armor"), "wolf_armor");

    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(WOLF_ARMOR, () -> WolfArmorModel.getTexturedModelData(new Dilation(0.35f)));

        // --------- / GECKOLIB ENTITY-RENDERING / --------- //

        entityRendering();

        // --------- / BLOCK RENDERING / --------- //

        blockRendering();
    }

    private void blockRendering() {

        // --------- / FLOWERS / --------- //

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DAFFODIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DAHLIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SWAMPOOL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DUSTY_MILLER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLACK_SPOT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AMBER_ANGEL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.AMARYLLIS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPORELOOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LIME_SPURGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.OSTRICH_FERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LUMISHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOUL_FLOWERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BLUE_IRIS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WILD_SMELLER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.HIBISCUS_ROSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BUTTERFLY_WEED, RenderLayer.getCutout());

        // --------- / POTTED FLOWERS / --------- //

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_DAFFODIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_DAHLIA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SWAMPOOL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_DUSTY_MILLER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BLACK_SPOT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_AMBER_ANGEL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_AMARYLLIS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SPORELOOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_LIME_SPURGE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_OSTRICH_FERN, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_LUMISHROOM, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_SOUL_FLOWERS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BLUE_IRIS, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_WILD_SMELLER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_HIBISCUS_ROSE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_BUTTERFLY_WEED, RenderLayer.getCutout());
    }

    private void entityRendering() {

        // --------- / MOB VOTE 2023 / --------- //

        EntityRendererRegistry.register(ModEntities.CRAB, CrabRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARMADILLO, ArmadilloRenderer::new);
        EntityRendererRegistry.register(ModEntities.PENGUIN, PenguinRenderer::new);

        // --------- / MOB VOTE 2022 / --------- //



        // --------- / MOB VOTE 2021 / --------- //

        EntityRendererRegistry.register(ModEntities.COPPER_GOLEM, CopperGolemRenderer::new);

        // --------- / MOB VOTE 2020 / --------- //

        EntityRendererRegistry.register(ModEntities.MOOBLOOM, MoobloomRenderer::new);

        // --------- / BIOME CHOOSER 2019 / --------- //



        // --------- / BIOME CHOOSER 2018 / --------- //



        // --------- / MOB VOTE 2017 / --------- //



    }
}
