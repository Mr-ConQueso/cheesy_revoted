package net.mrconqueso.entity.client;

import net.minecraft.util.Identifier;
import net.mrconqueso.CheesyRevoted;
import net.mrconqueso.entity.custom.ArmadilloEntity;
import software.bernie.geckolib.model.GeoModel;

public class ArmadilloModel extends GeoModel<ArmadilloEntity> {
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
}
