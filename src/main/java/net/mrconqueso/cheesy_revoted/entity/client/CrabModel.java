package net.mrconqueso.cheesy_revoted.entity.client;

import net.minecraft.util.Identifier;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.custom.CrabEntity;
import software.bernie.geckolib.model.GeoModel;

public class CrabModel extends GeoModel<CrabEntity> {
    @Override
    public Identifier getModelResource(CrabEntity animatable) {
        if (animatable.isBaby()) { return new Identifier(CheesyRevoted.MOD_ID, "geo/crab_baby.geo.json"); }

        return new Identifier(CheesyRevoted.MOD_ID, "geo/crab.geo.json");
    }

    @Override
    public Identifier getTextureResource(CrabEntity animatable) {
        if (animatable.isBaby()) { return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/crab_baby.png"); }

        return new Identifier(CheesyRevoted.MOD_ID, "textures/entity/crab.png");
    }

    @Override
    public Identifier getAnimationResource(CrabEntity animatable) {
        if (animatable.isBaby()) { return new Identifier(CheesyRevoted.MOD_ID, "animations/crab_baby.animation.json"); }

        return new Identifier(CheesyRevoted.MOD_ID, "animations/crab.animation.json");
    }
}
