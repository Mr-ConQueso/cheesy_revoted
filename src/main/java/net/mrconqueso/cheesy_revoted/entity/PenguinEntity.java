package net.mrconqueso.cheesy_revoted.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.mrconqueso.cheesy_revoted.blocks.PenguinEggBlock;
import net.mrconqueso.cheesy_revoted.registry.ModEntities;
import net.mrconqueso.cheesy_revoted.entity.goals.penguin.*;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;

public class PenguinEntity extends AnimalEntity implements GeoEntity {
    public PenguinEntity(EntityType<? extends PenguinEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.setPathfindingPenalty(PathNodeType.DOOR_IRON_CLOSED, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DOOR_WOOD_CLOSED, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DOOR_OPEN, -1.0f);
        this.moveControl = new PenguinMoveControl(this);
        this.setStepHeight(1.0f);
    }

    // --------- / VARIABLES / --------- //
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<BlockPos> TRAVEL_POS = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private static final TrackedData<Boolean> LAND_BOUND = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ACTIVELY_TRAVELING = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final Ingredient BREEDING_ITEM = Ingredient.ofItems(Items.SALMON);

    public void setTravelPos(BlockPos pos) {
        this.dataTracker.set(TRAVEL_POS, pos);
    }

    public BlockPos getTravelPos() {
        return this.dataTracker.get(TRAVEL_POS);
    }

    public boolean hasEgg() {
        return this.dataTracker.get(HAS_EGG);
    }

    public void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    public boolean isLandBound() {
        return this.dataTracker.get(LAND_BOUND);
    }

    void setLandBound(boolean landBound) {
        this.dataTracker.set(LAND_BOUND, landBound);
    }

    boolean isActivelyTraveling() {
        return this.dataTracker.get(ACTIVELY_TRAVELING);
    }

    public void setActivelyTraveling(boolean traveling) {
        this.dataTracker.set(ACTIVELY_TRAVELING, traveling);
    }
    
    // --------- / ATTRIBUTES & AI / --------- //
    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new PenguinEscapeDangerGoal(this, 1.2));
        this.goalSelector.add(1, new PenguinMateGoal(this, 1.0));
        this.goalSelector.add(1, new PenguinLayEggGoal(this, 1.0));
        this.goalSelector.add(2, new TemptGoal(this, 1.1, BREEDING_ITEM, false));
        this.goalSelector.add(3, new PenguinWanderInWaterGoal(this, 1.0));
        this.goalSelector.add(4, new PenguinTravelGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new PenguinWanderOnLandGoal(this, 1.0, 100));
    }

    // --------- / CHILD / --------- //
    @Override
    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.PENGUIN.create(world);
    }

    // --------- / ANIMATIONS / --------- //

    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("misc.idle");
    public static final RawAnimation WATER_IDLE = RawAnimation.begin().thenLoop("misc.water_idle");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("move.walk");
    public static final RawAnimation SWIM = RawAnimation.begin().thenLoop("move.swim");
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(penguinAnimationController(this));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public <T extends Entity & GeoAnimatable> AnimationController<T> penguinAnimationController(T entity) {
        return new AnimationController<T>(entity, "Swim/Walk/Idle", 1, state -> {
            if (entity.isTouchingWater()) {
                return state.setAndContinue(state.isMoving() ? SWIM : WATER_IDLE);
            }
            else if (state.isMoving()) {
                return state.setAndContinue(WALK);
            }
            else {
                return state.setAndContinue(IDLE);
            }
        });
    }

    private boolean isMoving() {
        if (this.getMovementSpeed() > 0) { return true; }
        return false;
    }

    // --------- / INTERACTIONS / --------- //
    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SALMON);
    }

    // --------- / SOUNDS / --------- //
    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        if (!this.isTouchingWater() && this.isOnGround() && !this.isBaby()) {
            return SoundEvents.ENTITY_TURTLE_AMBIENT_LAND;
        }
        return super.getAmbientSound();
    }

    @Override
    protected void playSwimSound(float volume) {
        super.playSwimSound(volume * 1.5f);
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.ENTITY_TURTLE_SWIM;
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        if (this.isBaby()) {
            return SoundEvents.ENTITY_TURTLE_HURT_BABY;
        }
        return SoundEvents.ENTITY_TURTLE_HURT;
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        if (this.isBaby()) {
            return SoundEvents.ENTITY_TURTLE_DEATH_BABY;
        }
        return SoundEvents.ENTITY_TURTLE_DEATH;
    }

    @Override
    protected float calculateNextStepSoundDistance() {
        return this.distanceTraveled + 0.15f;
    }
    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        SoundEvent soundEvent = this.isBaby() ? SoundEvents.ENTITY_TURTLE_SHAMBLE_BABY : SoundEvents.ENTITY_TURTLE_SHAMBLE;
        this.playSound(soundEvent, 0.15f, 1.0f);
    }

    // --------- / NBT-DATA / --------- //
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HAS_EGG, false);
        this.dataTracker.startTracking(TRAVEL_POS, BlockPos.ORIGIN);
        this.dataTracker.startTracking(LAND_BOUND, false);
        this.dataTracker.startTracking(ACTIVELY_TRAVELING, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("HasEgg", this.hasEgg());
        nbt.putInt("TravelPosX", this.getTravelPos().getX());
        nbt.putInt("TravelPosY", this.getTravelPos().getY());
        nbt.putInt("TravelPosZ", this.getTravelPos().getZ());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHasEgg(nbt.getBoolean("HasEgg"));
        int l = nbt.getInt("TravelPosX");
        int m = nbt.getInt("TravelPosY");
        int n = nbt.getInt("TravelPosZ");
        this.setTravelPos(new BlockPos(l, m, n));
    }

    // --------- / ENTITY SETTINGS / --------- //
    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.AQUATIC;
    }

    @Override
    public boolean canEat() {
        return super.canEat() && !this.hasEgg();
    }
    
    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    // --------- / START & SPAWNING / --------- //
    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setTravelPos(BlockPos.ORIGIN);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Deprecated
    public static boolean canSpawn(EntityType<PenguinEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return pos.getY() < world.getSeaLevel() + 4 && PenguinEggBlock.isGravelBelow(world, pos) && PenguinEntity.isLightLevelValidForNaturalSpawn(world, pos);
    }

    // --------- / ENTITY GOALS / --------- //
    public static class PenguinSwimNavigation extends AmphibiousSwimNavigation {
        PenguinSwimNavigation(PenguinEntity owner, World world) {
            super(owner, world);
        }

        @Override
        public boolean isValidPosition(BlockPos pos) {
            MobEntity mobEntity = this.entity;
            if (mobEntity instanceof PenguinEntity && ((PenguinEntity) mobEntity).isActivelyTraveling()) {
                return this.world.getBlockState(pos).isOf(Blocks.WATER);
            }
            return !this.world.getBlockState(pos.down()).isAir();
        }
    }
    @Override
    protected EntityNavigation createNavigation(World world) {
        return new PenguinSwimNavigation(this, world);
    }
    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        if (!this.isLandBound() && world.getFluidState(pos).isIn(FluidTags.WATER)) {
            return 10.0f;
        }
        if (PenguinEggBlock.isGravelBelow(world, pos)) {
            return 10.0f;
        }
        return world.getPhototaxisFavor(pos);
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isLogicalSideForUpdatingMovement() && this.isTouchingWater()) {
            this.updateVelocity(0.1f, movementInput);
            this.move(MovementType.SELF, this.getVelocity());
            this.setVelocity(this.getVelocity().multiply(0.9));
            if (!(this.getTarget() != null || this.isLandBound())) {
                this.setVelocity(this.getVelocity().add(0.0, -0.005, 0.0));
            }
        } else {
            super.travel(movementInput);
        }
    }

    // --------- / MODEL SETTINGS / --------- //
    @Override
    protected Vector3f getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return new Vector3f(0.0f, dimensions.height + (this.isBaby() ? 0.0f : 0.15625f) * scaleFactor, -0.25f * scaleFactor);
    }
    /*
    @Override
    public int getMaxLookPitchChange() {
        if (this.isInSittingPose()) {
            return 20;
        }
        return super.getMaxLookPitchChange();
    }

     */
}
