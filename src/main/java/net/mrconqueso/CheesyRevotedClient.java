package net.mrconqueso;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.mrconqueso.entity.ModEntities;
import net.mrconqueso.entity.client.ArmadilloRenderer;
import net.mrconqueso.entity.client.CrabRenderer;
import net.mrconqueso.entity.client.PenguinRenderer;

public class CheesyRevotedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.CRAB, CrabRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARMADILLO, ArmadilloRenderer::new);
        EntityRendererRegistry.register(ModEntities.PENGUIN, PenguinRenderer::new);

    }
}
