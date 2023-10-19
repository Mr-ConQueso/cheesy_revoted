package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.custom.MoobloomEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MoobloomModel extends GeoModel<MoobloomEntity> {

    // --------- / RESOURCES / --------- //
    @Override
    public Identifier getModelResource(MoobloomEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "geo/moobloom.geo.json");
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
