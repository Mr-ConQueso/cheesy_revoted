package net.mrconqueso.cheesy_revoted.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.MoobloomEntity;
import net.mrconqueso.cheesy_revoted.entity.variants.MoobloomVariant;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.Map;

public class MoobloomModel extends GeoModel<MoobloomEntity> {

    // --------- / FLOWER VARIANTS / --------- //
    public static final Map<MoobloomVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(MoobloomVariant.class), (map) -> {
                map.put(MoobloomVariant.DEFAULT,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_yellow.geo.json"));
                map.put(MoobloomVariant.WHITE,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_white.geo.json"));
                map.put(MoobloomVariant.LIGHT_GRAY,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_light_gray.geo.json"));
                map.put(MoobloomVariant.GRAY,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_gray.geo.json"));
                map.put(MoobloomVariant.BLACK,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_black.geo.json"));
                map.put(MoobloomVariant.BROWN,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_brown.geo.json"));
                map.put(MoobloomVariant.RED,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_red.geo.json"));
                map.put(MoobloomVariant.ORANGE,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_orange.geo.json"));
                map.put(MoobloomVariant.LIME,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_lime.geo.json"));
                map.put(MoobloomVariant.GREEN,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_green.geo.json"));
                map.put(MoobloomVariant.CYAN,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_cyan.geo.json"));
                map.put(MoobloomVariant.LIGHT_BLUE,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_light_blue.geo.json"));
                map.put(MoobloomVariant.BLUE,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_blue.geo.json"));
                map.put(MoobloomVariant.PURPLE,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_purple.geo.json"));
                map.put(MoobloomVariant.MAGENTA,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_magenta.geo.json"));
                map.put(MoobloomVariant.PINK,
                        new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom/moobloom_pink.geo.json"));
            });

    // --------- / RESOURCES / --------- //
    @Override
    public Identifier getModelResource(MoobloomEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getTextureResource(MoobloomEntity animatable) {
        return MoobloomRenderer.LOCATION_BY_VARIANT.get(animatable.getVariant());
    }

    @Override
    public Identifier getAnimationResource(MoobloomEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "animations/moobloom.animation.json");
    }

    // --------- / ROTATE HEAD / --------- //

    @Override
    public void setCustomAnimations(MoobloomEntity animatable, long instanceId, AnimationState<MoobloomEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setPosY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
