package net.mrconqueso.cheesy_revoted.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.item.ItemStack;
import net.mrconqueso.cheesy_revoted.item.custom.WolfArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    // --------- / ENCHANTABLE ITEMS / --------- //
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void wolfArmorProtection(ItemStack stack, CallbackInfoReturnable<Boolean> info) {

        if((Object) this instanceof ProtectionEnchantment && stack.getItem() instanceof WolfArmorItem) {
            info.setReturnValue(true);
        }
    }
}
