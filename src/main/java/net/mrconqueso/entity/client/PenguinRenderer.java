package net.mrconqueso.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mrconqueso.CheesyRevoted;
import net.mrconqueso.entity.custom.PenguinEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PenguinRenderer extends GeoEntityRenderer<PenguinEntity> {

    public PenguinRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new PenguinModel());
    }

    @Override
    public Identifier getTextureLocation(PenguinEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/penguin.png");
    }

    @Override
    public void render(PenguinEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.6f, 0.6f, 0.6f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
