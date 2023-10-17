package net.mrconqueso.entity.client;

import net.minecraft.util.Identifier;
import net.mrconqueso.CheesyRevoted;
import net.mrconqueso.entity.custom.PenguinEntity;
import software.bernie.geckolib.model.GeoModel;

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
}
