package net.mrconqueso.cheesy_revoted.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ItemScatterer;
import net.minecraft.world.World;
import net.mrconqueso.cheesy_revoted.implementation.WolfDataAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity implements WolfDataAccessor {
    private WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    // --------- / VARIABLES / --------- //
    @Unique private static final TrackedData<ItemStack> WOLF_ARMOR = DataTracker.registerData(WolfEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    // --------- / ARMOR DATA / --------- //
    @Inject(method = "initDataTracker", at = @At("HEAD"))
    private void injectWolfArmorData(CallbackInfo ci) {
        dataTracker.startTracking(WOLF_ARMOR, ItemStack.EMPTY);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void writeArmorNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("WolfArmor", getWolfArmor().writeNbt(new NbtCompound()));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readArmorNbt(NbtCompound nbt, CallbackInfo ci) {
        if(nbt.contains("WolfArmor")) {
            setWolfArmor(ItemStack.fromNbt(nbt.getCompound("WolfArmor")));
        }
    }

    // --------- / RETURN ON DEATH / --------- //

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void dropArmorOnDeath(DamageSource source, CallbackInfo ci) {
        ItemStack armor = getWolfArmor();
        if(!armor.isEmpty()) {
            ItemScatterer.spawn(getWorld(), getX(), getY(), getZ(), armor);
        }
    }


    // --------- / DATA ACCESSORS / --------- //
    @Override
    public void setWolfArmor(ItemStack stack) {
        dataTracker.set(WOLF_ARMOR, stack);
    }

    @Override
    public ItemStack getWolfArmor() {
        return dataTracker.get(WOLF_ARMOR);
    }
}
