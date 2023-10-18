package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.custom.CrabEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrabRenderer extends GeoEntityRenderer<CrabEntity> {

    public CrabRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CrabModel());
    }

    @Override
    public Identifier getTextureLocation(CrabEntity animatable) {
        if (animatable.isBaby()) { return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/crab_baby.png"); }

        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/crab.png");
    }

    @Override
    public void render(CrabEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.55f, 0.55f, 0.55f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
