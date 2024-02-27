package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.SnifferEntityRevoted;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SnifferModel extends GeoModel<SnifferEntityRevoted> {

    // --------- / RESOURCES / --------- //
    @Override
    public Identifier getModelResource(SnifferEntityRevoted animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "geo/sniffer.geo.json");
    }

    @Override
    public Identifier getTextureResource(SnifferEntityRevoted animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/sniffer.png");
    }

    @Override
    public Identifier getAnimationResource(SnifferEntityRevoted animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "animations/sniffer.animation.json");
    }

    // --------- / ROTATE HEAD / --------- //
    @Override
    public void setCustomAnimations(SnifferEntityRevoted animatable, long instanceId, AnimationState<SnifferEntityRevoted> animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");

        if (head != null) {
            EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * MathHelper.RADIANS_PER_DEGREE);
            head.setPosY(entityData.netHeadYaw() * MathHelper.RADIANS_PER_DEGREE);
        }
    }
}
