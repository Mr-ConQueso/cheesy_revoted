package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.RascalEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RascalRenderer extends GeoEntityRenderer<RascalEntity> {
    public RascalRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new RascalModel());
    }

    @Override
    public Identifier getTextureLocation(RascalEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/rascal.png");
    }
}
