package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.CopperGolemEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CopperGolemModel extends GeoModel<CopperGolemEntity> {

    // --------- / RESOURCES / --------- //
    @Override
    public Identifier getModelResource(CopperGolemEntity animatable) {
        if (animatable.isBaby()) { return new Identifier(CheesyRevoted.MOD_ID, "geo/copper_golem_baby.geo.json"); }

        return new Identifier(CheesyRevoted.MOD_ID, "geo/copper_golem.geo.json");
    }

    @Override
    public Identifier getTextureResource(CopperGolemEntity animatable) {
        if (animatable.isBaby()) { return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/copperGolem_baby.png"); }

        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/copper_golem/copper_golem.png");
    }

    @Override
    public Identifier getAnimationResource(CopperGolemEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "animations/copper_golem.animation.json");
    }

    // --------- / ROTATE HEAD / --------- //
    @Override
    public void setCustomAnimations(CopperGolemEntity animatable, long instanceId, AnimationState<CopperGolemEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setPosY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
