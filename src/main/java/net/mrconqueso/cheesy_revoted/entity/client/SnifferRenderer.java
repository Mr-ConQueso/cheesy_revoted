package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.SnifferEntityRevoted;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SnifferRenderer extends GeoEntityRenderer<SnifferEntityRevoted> {
    public SnifferRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new SnifferModel());
    }

    @Override
    public Identifier getTextureLocation(SnifferEntityRevoted animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/sniffer.png");
    }

    // --------- / BABY RENDERER / --------- //
    @Override
    public void render(SnifferEntityRevoted entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.45f, 0.45f, 0.45f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
