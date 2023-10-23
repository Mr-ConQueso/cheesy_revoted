package net.mrconqueso.cheesy_revoted.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import net.mrconqueso.cheesy_revoted.item.custom.WolfArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getArmor", at = @At("HEAD"), cancellable = true)
    private void getArmor(CallbackInfoReturnable<Integer> cir) {
        if((Object) this instanceof WolfEntity wolf) {
            ItemStack stack = CheesyRevoted.getData(wolf).getWolfArmor();

            if(stack.getItem() instanceof WolfArmorItem armorItem) {
                cir.setReturnValue(armorItem.getBonus(stack));
            }
        }
    }
}
