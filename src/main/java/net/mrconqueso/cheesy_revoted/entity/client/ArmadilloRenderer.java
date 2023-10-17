package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.custom.ArmadilloEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArmadilloRenderer extends GeoEntityRenderer<ArmadilloEntity> {

    public ArmadilloRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new ArmadilloModel());
    }

    @Override
    public Identifier getTextureLocation(ArmadilloEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/armadillo.png");
    }

    @Override
    public void render(ArmadilloEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.55f, 0.55f, 0.55f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
