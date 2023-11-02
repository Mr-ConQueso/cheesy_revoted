package net.mrconqueso.cheesy_revoted.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.MoobloomEntity;
import net.mrconqueso.cheesy_revoted.entity.variants.MoobloomVariant;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class MoobloomRenderer extends GeoEntityRenderer<MoobloomEntity> {
    public MoobloomRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new MoobloomModel());
    }

    // --------- / FLOWER VARIANTS / --------- //
    public static final Map<MoobloomVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MoobloomVariant.class), (map) -> {
                map.put(MoobloomVariant.DEFAULT,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_yellow.png"));
                map.put(MoobloomVariant.WHITE,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_white.png"));
                map.put(MoobloomVariant.LIGHT_GRAY,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_light_gray.png"));
                map.put(MoobloomVariant.GRAY,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_gray.png"));
                map.put(MoobloomVariant.BLACK,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_black.png"));
                map.put(MoobloomVariant.BROWN,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_brown.png"));
                map.put(MoobloomVariant.RED,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_red.png"));
                map.put(MoobloomVariant.ORANGE,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_orange.png"));
                map.put(MoobloomVariant.LIME,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_lime.png"));
                map.put(MoobloomVariant.GREEN,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_green.png"));
                map.put(MoobloomVariant.CYAN,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_cyan.png"));
                map.put(MoobloomVariant.LIGHT_BLUE,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_light_blue.png"));
                map.put(MoobloomVariant.BLUE,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_blue.png"));
                map.put(MoobloomVariant.PURPLE,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_purple.png"));
                map.put(MoobloomVariant.MAGENTA,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_magenta.png"));
                map.put(MoobloomVariant.PINK,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/moobloom/moobloom_pink.png"));
            });

    @Override
    public Identifier getTextureLocation(MoobloomEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    // --------- / BABY RENDERER / --------- //
    @Override
    public void render(MoobloomEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
                       VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.55f, 0.55f, 0.55f);
        }
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
