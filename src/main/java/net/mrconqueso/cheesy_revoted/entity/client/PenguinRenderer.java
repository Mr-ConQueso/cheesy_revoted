package net.mrconqueso.cheesy_revoted.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.custom.PenguinEntity;
import net.mrconqueso.cheesy_revoted.entity.variants.PenguinVariant;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class PenguinRenderer extends GeoEntityRenderer<PenguinEntity> {
    public PenguinRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new PenguinModel());
    }
    /*
    public static final Map<PenguinVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(PenguinVariant.class), (map) -> {
                map.put(PenguinVariant.DEFAULT,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/penguin.png"));
                map.put(PenguinVariant.BABY,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/penguin_baby.png"));
            });

     */

    @Override
    public Identifier getTextureLocation(PenguinEntity animatable) {
        //return LOCATION_BY_VARIANT.get(ins);
        if (animatable.isBaby()) { return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/penguin_baby.png"); }

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
