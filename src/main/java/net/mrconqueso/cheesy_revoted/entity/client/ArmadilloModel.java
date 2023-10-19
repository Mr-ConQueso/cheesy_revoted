package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.custom.ArmadilloEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class ArmadilloModel extends GeoModel<ArmadilloEntity> {

    // --------- / RESOURCES / --------- //
    @Override
    public Identifier getModelResource(ArmadilloEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "geo/armadillo.geo.json");
    }

    @Override
    public Identifier getTextureResource(ArmadilloEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/armadillo.png");
    }

    @Override
    public Identifier getAnimationResource(ArmadilloEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "animations/armadillo.animation.json");
    }

    // --------- / ROTATE HEAD / --------- //
    @Override
    public void setCustomAnimations(ArmadilloEntity animatable, long instanceId, AnimationState<ArmadilloEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setPosY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
