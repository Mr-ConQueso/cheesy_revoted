package net.mrconqueso.cheesy_revoted.client.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.CheesyRevotedClient;
import net.mrconqueso.cheesy_revoted.client.WolfArmorModel;
import net.mrconqueso.cheesy_revoted.item.WolfArmorItem;

@Environment(EnvType.CLIENT)
public class WolfArmorFeatureRenderer extends FeatureRenderer<WolfEntity, WolfEntityModel<WolfEntity>> {
    public WolfArmorFeatureRenderer(FeatureRendererContext<WolfEntity, WolfEntityModel<WolfEntity>> featureRendererContext, EntityModelLoader loader) {
        super(featureRendererContext);
        model = new WolfArmorModel(loader.getModelPart(CheesyRevotedClient.WOLF_ARMOR));
    }

    // --------- / VARIABLES / --------- //
    private final WolfArmorModel model;

    // --------- / RENDER WOLF ARMOR / --------- //
    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, WolfEntity wolf, float f, float g, float h, float j, float k, float l) {
        ItemStack itemStack = CheesyRevoted.getData(wolf).getWolfArmor();

        if (itemStack.getItem() instanceof WolfArmorItem armorItem) {
            this.getContextModel().copyStateTo(this.model);

            this.model.animateModel(wolf, f, g, h);
            this.model.setAngles(wolf, f, g, j, k, l);

            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(armorItem.getEntityTexture()), false, itemStack.hasEnchantments());
            this.model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}

