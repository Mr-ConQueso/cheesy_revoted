package net.mrconqueso.cheesy_revoted.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.registry.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    // --------- / 3D RENDER ON HAND / --------- //
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useCrabClawModel(BakedModel value, ItemStack itemStack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        boolean isInInventory;
        if (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.FIXED || renderMode == ModelTransformationMode.GROUND) {
            isInInventory = true;
        } else { isInInventory = false; }

        // Crab Claw 3D Model
        if (itemStack.isOf(ModItems.CRAB_CLAW) && !isInInventory) {
            return ((ItemRendererAccessor) this).mccourse$getModels().getModelManager().getModel(new ModelIdentifier(CheesyRevoted.MOD_ID, "crab_claw_model", "inventory"));
        }
        return value;
    }
}
