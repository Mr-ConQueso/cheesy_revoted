package net.mrconqueso.cheesy_revoted.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.CopperGolemEntity;
import net.mrconqueso.cheesy_revoted.entity.CopperGolemEntity;
import net.mrconqueso.cheesy_revoted.entity.variants.CopperGolemVariant;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class CopperGolemRenderer extends GeoEntityRenderer<CopperGolemEntity> {
    public CopperGolemRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new CopperGolemModel());
    }

    public static final Map<CopperGolemVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(CopperGolemVariant.class), (map) -> {
                map.put(CopperGolemVariant.DEFAULT,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/copper_golem/copper_golem.png"));
                map.put(CopperGolemVariant.EXPOSED,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/copper_golem/copper_golem_exposed.png"));
                map.put(CopperGolemVariant.WEATHERED,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/copper_golem/copper_golem_weathered.png"));
                map.put(CopperGolemVariant.OXIDIZED,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/copper_golem/copper_golem_oxidized.png"));
            });
    @Override
    public Identifier getTextureLocation(CopperGolemEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}
