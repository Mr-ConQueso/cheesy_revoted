package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.custom.PenguinEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class PenguinModel extends GeoModel<PenguinEntity> {
    @Override
    public Identifier getModelResource(PenguinEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "geo/penguin.geo.json");
    }

    @Override
    public Identifier getTextureResource(PenguinEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/penguin.png");
    }

    @Override
    public Identifier getAnimationResource(PenguinEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "animations/penguin.animation.json");
    }

    @Override
    public void setCustomAnimations(PenguinEntity animatable, long instanceId, AnimationState<PenguinEntity> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setPosY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
