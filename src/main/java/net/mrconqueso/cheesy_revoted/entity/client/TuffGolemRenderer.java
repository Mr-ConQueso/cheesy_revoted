package net.mrconqueso.cheesy_revoted.entity.client;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.entity.CopperGolemEntity;
import net.mrconqueso.cheesy_revoted.entity.TuffGolemEntity;
import net.mrconqueso.cheesy_revoted.entity.variants.TuffGolemVariant;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class TuffGolemRenderer extends GeoEntityRenderer<TuffGolemEntity> {
    public TuffGolemRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new TuffGolemModel());
    }

    public static final Map<TuffGolemVariant, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(TuffGolemVariant.class), (map) -> {
                map.put(TuffGolemVariant.DEFAULT,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_yellow.png"));
                map.put(TuffGolemVariant.WHITE,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_white.png"));
                map.put(TuffGolemVariant.LIGHT_GRAY,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_light_gray.png"));
                map.put(TuffGolemVariant.GRAY,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_gray.png"));
                map.put(TuffGolemVariant.BLACK,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_black.png"));
                map.put(TuffGolemVariant.BROWN,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_brown.png"));
                map.put(TuffGolemVariant.RED,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_red.png"));
                map.put(TuffGolemVariant.ORANGE,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_orange.png"));
                map.put(TuffGolemVariant.LIME,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_lime.png"));
                map.put(TuffGolemVariant.GREEN,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_green.png"));
                map.put(TuffGolemVariant.CYAN,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_cyan.png"));
                map.put(TuffGolemVariant.LIGHT_BLUE,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_light_blue.png"));
                map.put(TuffGolemVariant.BLUE,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_blue.png"));
                map.put(TuffGolemVariant.PURPLE,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_purple.png"));
                map.put(TuffGolemVariant.MAGENTA,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_magenta.png"));
                map.put(TuffGolemVariant.PINK,
                        new Identifier(CheesyRevoted.MOD_ID, "textures/entity/tuff_golem/tuff_golem_pink.png"));
            });
    @Override
    public Identifier getTextureLocation(TuffGolemEntity animatable) {
        return LOCATION_BY_VARIANT.get(animatable.getVariant());
    }
}
