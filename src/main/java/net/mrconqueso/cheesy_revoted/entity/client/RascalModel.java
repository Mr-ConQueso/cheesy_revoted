package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.RascalEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class RascalModel extends GeoModel<RascalEntity> {

    // --------- / RESOURCES / --------- //
    @Override
    public Identifier getModelResource(RascalEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "geo/rascal.geo.json");
    }

    @Override
    public Identifier getTextureResource(RascalEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/rascal.png");
    }

    @Override
    public Identifier getAnimationResource(RascalEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "animations/rascal.animation.json");
    }

    // --------- / ROTATE HEAD / --------- //
    @Override
    public void setCustomAnimations(RascalEntity animatable, long instanceId, AnimationState<RascalEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setPosY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
