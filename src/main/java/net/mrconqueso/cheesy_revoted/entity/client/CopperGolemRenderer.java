package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.CopperGolemEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CopperGolemRenderer extends GeoEntityRenderer<CopperGolemEntity> {
    public CopperGolemRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CopperGolemModel());
    }

    @Override
    public Identifier getTextureLocation(CopperGolemEntity animatable) {
        if (animatable.isBaby()) { return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/copper_golem_baby.png"); }

        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/copper_golem/copper_golem.png");
    }
}
