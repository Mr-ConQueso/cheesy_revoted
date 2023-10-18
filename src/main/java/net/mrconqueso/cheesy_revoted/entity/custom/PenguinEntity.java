package net.mrconqueso.cheesy_revoted.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;
import net.mrconqueso.cheesy_revoted.entity.goals.PenguinController;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class PenguinEntity extends AnimalEntity implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.SALMON);
    public PenguinEntity(EntityType<? extends AnimalEntity> entityType, World world) { super(entityType, world); }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.10f);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.PENGUIN.create(world);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
    }
    private PlayState predicate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {

        if (geoAnimatableAnimationState.isMoving() && !this.isSwimming()) {
            geoAnimatableAnimationState.setAnimation(RawAnimation.begin().thenLoop("move.walk"));

        } else if (this.isSwimming()) {
            geoAnimatableAnimationState.setAnimation(RawAnimation.begin().thenLoop("move.swim"));
        }

        geoAnimatableAnimationState.setAnimation(RawAnimation.begin().thenLoop("misc.idle"));

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {

        boolean bl = this.isBreedingItem(player.getStackInHand(hand));
        ActionResult actionResult = super.interactMob(player, hand);

        return actionResult;
    }
    @Override
    public boolean isBreedingItem(ItemStack stack) { return BREEDING_INGREDIENT.test(stack); }

    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isTouchingWater()) {
            return SoundEvents.ENTITY_DROWNED_AMBIENT_WATER;
        }
        return SoundEvents.ENTITY_DROWNED_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        if (this.isTouchingWater()) {
            return SoundEvents.ENTITY_DROWNED_HURT_WATER;
        }
        return SoundEvents.ENTITY_DROWNED_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        if (this.isTouchingWater()) {
            return SoundEvents.ENTITY_DROWNED_DEATH_WATER;
        }
        return SoundEvents.ENTITY_DROWNED_DEATH;
    }

    @Override
    protected SoundEvent getSwimSound() { return SoundEvents.ENTITY_PIG_AMBIENT; }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f);
    }
}
