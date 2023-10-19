package net.mrconqueso.cheesy_revoted;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;
import net.mrconqueso.cheesy_revoted.entity.client.ArmadilloRenderer;
import net.mrconqueso.cheesy_revoted.entity.client.CrabRenderer;
import net.mrconqueso.cheesy_revoted.entity.client.MoobloomRenderer;
import net.mrconqueso.cheesy_revoted.entity.client.PenguinRenderer;

public class CheesyRevotedClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        // --------- / GECKOLIB ENTITY-RENDERING / --------- //

        EntityRendererRegistry.register(ModEntities.CRAB, CrabRenderer::new);
        EntityRendererRegistry.register(ModEntities.ARMADILLO, ArmadilloRenderer::new);
        EntityRendererRegistry.register(ModEntities.PENGUIN, PenguinRenderer::new);
        EntityRendererRegistry.register(ModEntities.MOOBLOOM, MoobloomRenderer::new);

    }
}
