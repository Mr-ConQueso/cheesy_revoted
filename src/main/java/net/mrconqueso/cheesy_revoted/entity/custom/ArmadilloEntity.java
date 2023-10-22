package net.mrconqueso.cheesy_revoted.entity.custom;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;
import net.mrconqueso.cheesy_revoted.item.ModItems;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

public class ArmadilloEntity extends AnimalEntity implements GeoEntity {
    public ArmadilloEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    // --------- / VARIABLES / --------- //
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.GOLD_NUGGET);

    // --------- / ATTRIBUTES & AI / --------- //
    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25f);
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new FollowParentGoal(this, 1.25));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(5, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    // --------- / CHILD & GROW-UP / --------- //
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.ARMADILLO.create(world);
    }
    @Override
    protected void onGrowUp() {
        super.onGrowUp();
        if (!this.isBaby() && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            this.dropItem(ModItems.ARMADILLO_SCUTE, 1);
        }
    }
    // --------- / ANIMATIONS / --------- //

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(DefaultAnimations.genericWalkIdleController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // --------- / INTERACTIONS / --------- //
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {

        boolean bl = this.isBreedingItem(player.getStackInHand(hand));
        ActionResult actionResult = super.interactMob(player, hand);

        return actionResult;
    }
    @Override
    public boolean isBreedingItem(ItemStack stack) { return BREEDING_INGREDIENT.test(stack); }

    // --------- / SOUNDS / --------- //
    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_PIG_AMBIENT; }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return SoundEvents.ENTITY_PIG_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_PIG_DEATH; }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) { this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f); }

    // --------- / MODEL SETTINGS / --------- //
    @Override
    protected Vec3d getLeashOffset() { return new Vec3d(0.0, this.getStandingEyeHeight(), this.getWidth() * -0.94f); }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) { return dimensions.height * 0.333f; }

    @Override
    protected Vector3f getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return new Vector3f(0.0f, dimensions.height - 0.0625f * scaleFactor, 0.0f);
    }
}
