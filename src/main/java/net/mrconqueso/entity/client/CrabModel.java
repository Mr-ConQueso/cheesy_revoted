package net.mrconqueso.entity.client;

import net.minecraft.util.Identifier;
import net.mrconqueso.CheesyRevoted;
import net.mrconqueso.entity.custom.CrabEntity;
import software.bernie.geckolib.model.GeoModel;

public class CrabModel extends GeoModel<CrabEntity> {
    @Override
    public Identifier getModelResource(CrabEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "geo/crab.geo.json");
    }

    @Override
    public Identifier getTextureResource(CrabEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/crab.png");
    }

    @Override
    public Identifier getAnimationResource(CrabEntity animatable) {
        return new Identifier(CheesyRevoted.MOD_ID, "animations/crab.animation.json");
    }
}
