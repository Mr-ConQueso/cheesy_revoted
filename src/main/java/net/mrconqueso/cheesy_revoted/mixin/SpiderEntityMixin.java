package net.mrconqueso.cheesy_revoted.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import net.mrconqueso.cheesy_revoted.entity.ArmadilloEntityRevoted;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends HostileEntity {
    protected SpiderEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    // --------- / ATTRIBUTES & AI / --------- //
    @Inject(method = "initGoals", at = @At("RETURN"))
    protected void injectNewGoals(CallbackInfo ci) {
        this.goalSelector.add(3, new FleeEntityGoal<ArmadilloEntityRevoted>(this, ArmadilloEntityRevoted.class, 6.0f, 1.0, 1.2));
    }
}
