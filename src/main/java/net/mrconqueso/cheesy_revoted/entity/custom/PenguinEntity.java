package net.mrconqueso.cheesy_revoted.entity.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.control.MoveControl;
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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import net.mrconqueso.cheesy_revoted.blocks.ModBlocks;
import net.mrconqueso.cheesy_revoted.blocks.custom.PenguinEggBlock;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

public class PenguinEntity extends AnimalEntity implements GeoEntity {

    // --------- / VARIABLES / --------- //
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<BlockPos> TRAVEL_POS = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private static final TrackedData<Boolean> LAND_BOUND = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ACTIVELY_TRAVELING = DataTracker.registerData(PenguinEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final Ingredient BREEDING_ITEM = Ingredient.ofItems(Items.SALMON);

    public PenguinEntity(EntityType<? extends PenguinEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.setPathfindingPenalty(PathNodeType.DOOR_IRON_CLOSED, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DOOR_WOOD_CLOSED, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DOOR_OPEN, -1.0f);
        this.moveControl = new PenguinMoveControl(this);
        this.setStepHeight(1.0f);
    }

    void setTravelPos(BlockPos pos) {
        this.dataTracker.set(TRAVEL_POS, pos);
    }

    BlockPos getTravelPos() {
        return this.dataTracker.get(TRAVEL_POS);
    }

    public boolean hasEgg() {
        return this.dataTracker.get(HAS_EGG);
    }

    void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    boolean isLandBound() {
        return this.dataTracker.get(LAND_BOUND);
    }

    void setLandBound(boolean landBound) {
        this.dataTracker.set(LAND_BOUND, landBound);
    }

    boolean isActivelyTraveling() {
        return this.dataTracker.get(ACTIVELY_TRAVELING);
    }

    void setActivelyTraveling(boolean traveling) {
        this.dataTracker.set(ACTIVELY_TRAVELING, traveling);
    }

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

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setTravelPos(BlockPos.ORIGIN);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Deprecated
    public static boolean canSpawn(EntityType<PenguinEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return pos.getY() < world.getSeaLevel() + 4 && PenguinEggBlock.isSandBelow(world, pos) && PenguinEntity.isLightLevelValidForNaturalSpawn(world, pos);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new PenguinEscapeDangerGoal(this, 1.2));
        this.goalSelector.add(1, new MateGoal(this, 1.0));
        this.goalSelector.add(1, new LayEggGoal(this, 1.0));
        this.goalSelector.add(2, new TemptGoal(this, 1.1, BREEDING_ITEM, false));
        this.goalSelector.add(3, new WanderInWaterGoal(this, 1.0));
        this.goalSelector.add(4, new GoHomeGoal(this));
        this.goalSelector.add(7, new GoHomeGoal.TravelGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(9, new GoHomeGoal.WanderOnLandGoal(this, 1.0, 100));
    }
    
    public static DefaultAttributeContainer.Builder setAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }

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
    public int getMinAmbientSoundDelay() {
        return 200;
    }

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
    protected void playStepSound(BlockPos pos, BlockState state) {
        SoundEvent soundEvent = this.isBaby() ? SoundEvents.ENTITY_TURTLE_SHAMBLE_BABY : SoundEvents.ENTITY_TURTLE_SHAMBLE;
        this.playSound(soundEvent, 0.15f, 1.0f);
    }

    @Override
    public boolean canEat() {
        return super.canEat() && !this.hasEgg();
    }

    @Override
    protected float calculateNextStepSoundDistance() {
        return this.distanceTraveled + 0.15f;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new GoHomeGoal.PenguinSwimNavigation(this, world);
    }

    @Override
    @Nullable
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.PENGUIN.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(Items.SALMON);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        if (!this.isLandBound() && world.getFluidState(pos).isIn(FluidTags.WATER)) {
            return 10.0f;
        }
        if (PenguinEggBlock.isSandBelow(world, pos)) {
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

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Override
    protected Vector3f getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return new Vector3f(0.0f, dimensions.height + (this.isBaby() ? 0.0f : 0.15625f) * scaleFactor, -0.25f * scaleFactor);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    static class PenguinMoveControl extends MoveControl {
        private final PenguinEntity penguin;

        PenguinMoveControl(PenguinEntity penguin) {
            super(penguin);
            this.penguin = penguin;
        }

        private void updateVelocity() {
            if (this.penguin.isTouchingWater()) {
                this.penguin.setVelocity(this.penguin.getVelocity().add(0.0, 0.005, 0.0));
                if (this.penguin.isBaby()) {
                    this.penguin.setMovementSpeed(Math.max(this.penguin.getMovementSpeed() / 3.0f, 0.06f));
                }
            } else if (this.penguin.isOnGround()) {
                this.penguin.setMovementSpeed(Math.max(this.penguin.getMovementSpeed() / 2.0f, 0.06f));
            }
        }

        @Override
        public void tick() {
            double f;
            double e;
            this.updateVelocity();
            if (this.state != MoveControl.State.MOVE_TO || this.penguin.getNavigation().isIdle()) {
                this.penguin.setMovementSpeed(0.0f);
                return;
            }
            double d = this.targetX - this.penguin.getX();
            double g = Math.sqrt(d * d + (e = this.targetY - this.penguin.getY()) * e + (f = this.targetZ - this.penguin.getZ()) * f);
            if (g < (double)1.0E-5f) {
                this.entity.setMovementSpeed(0.0f);
                return;
            }
            e /= g;
            float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875) - 90.0f;
            this.penguin.setYaw(this.wrapDegrees(this.penguin.getYaw(), h, 90.0f));
            this.penguin.bodyYaw = this.penguin.getYaw();
            float i = (float)(this.speed * this.penguin.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
            this.penguin.setMovementSpeed(MathHelper.lerp(0.125f, this.penguin.getMovementSpeed(), i));
            this.penguin.setVelocity(this.penguin.getVelocity().add(0.0, (double)this.penguin.getMovementSpeed() * e * 0.1, 0.0));
        }
    }

    static class PenguinEscapeDangerGoal extends EscapeDangerGoal {
        PenguinEscapeDangerGoal(PenguinEntity penguin, double speed) {
            super(penguin, speed);
        }

        @Override
        public boolean canStart() {
            if (!this.isInDanger()) {
                return false;
            }
            BlockPos blockPos = this.locateClosestWater(this.mob.getWorld(), this.mob, 7);
            if (blockPos != null) {
                this.targetX = blockPos.getX();
                this.targetY = blockPos.getY();
                this.targetZ = blockPos.getZ();
                return true;
            }
            return this.findTarget();
        }
    }

    static class MateGoal
            extends AnimalMateGoal {
        private final PenguinEntity penguin;

        MateGoal(PenguinEntity penguin, double speed) {
            super(penguin, speed);
            this.penguin = penguin;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.penguin.hasEgg();
        }

        @Override
        protected void breed() {
            ServerPlayerEntity serverPlayerEntity = this.animal.getLovingPlayer();
            if (serverPlayerEntity == null) {
                assert this.mate != null;
                if (this.mate.getLovingPlayer() != null) {
                    serverPlayerEntity = this.mate.getLovingPlayer();
                }
            }
            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.mate, null);
            }
            this.penguin.setHasEgg(true);
            this.animal.setBreedingAge(6000);
            assert this.mate != null;
            this.mate.setBreedingAge(6000);
            this.animal.resetLoveTicks();
            this.mate.resetLoveTicks();
            Random random = this.animal.getRandom();
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), random.nextInt(7) + 1));
            }
        }
    }

    static class LayEggGoal
            extends MoveToTargetPosGoal {
        private final PenguinEntity penguin;

        LayEggGoal(PenguinEntity penguin, double speed) {
            super(penguin, speed, 16);
            this.penguin = penguin;
        }

        @Override
        public boolean canStart() {
            if (this.penguin.hasEgg()) {
                return super.canStart();
            }
            return false;
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && this.penguin.hasEgg();
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos blockPos = this.penguin.getBlockPos();
            if (!this.penguin.isTouchingWater() && this.hasReached()) {

                World world = this.penguin.getWorld();
                world.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
                BlockPos blockPos2 = this.targetPos.up();
                BlockState blockState = (BlockState) ModBlocks.PENGUIN_EGG.getDefaultState().with(PenguinEggBlock.EGGS, this.penguin.random.nextInt(2) + 1);
                world.setBlockState(blockPos2, blockState, Block.NOTIFY_ALL);
                world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this.penguin, blockState));
                this.penguin.setHasEgg(false);
                this.penguin.setLoveTicks(600);
            }
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            if (!world.isAir(pos.up())) {
                return false;
            }
            return PenguinEggBlock.isSand(world, pos);
        }
    }

    static class WanderInWaterGoal extends MoveToTargetPosGoal {
        private final PenguinEntity penguin;

        WanderInWaterGoal(PenguinEntity penguin, double speed) {
            super(penguin, penguin.isBaby() ? 2.0 : speed, 24);
            this.penguin = penguin;
            this.lowestY = -1;
        }

        @Override
        public boolean shouldContinue() {
            return !this.penguin.isTouchingWater() && this.tryingTime <= 1200 && this.isTargetPos(this.penguin.getWorld(), this.targetPos);
        }

        @Override
        public boolean canStart() {
            if (this.penguin.isBaby() && !this.penguin.isTouchingWater()) {
                return super.canStart();
            }
            if (!(this.penguin.isLandBound() || this.penguin.isTouchingWater() || this.penguin.hasEgg())) {
                return super.canStart();
            }
            return false;
        }

        @Override
        public boolean shouldResetPath() {
            return this.tryingTime % 160 == 0;
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            return world.getBlockState(pos).isOf(Blocks.WATER);
        }
    }

    static class GoHomeGoal extends Goal {
        private final PenguinEntity penguin;

        GoHomeGoal(PenguinEntity penguin) {
            this.penguin = penguin;
        }

        @Override
        public boolean canStart() {
            if (this.penguin.isBaby()) {
                return false;
            }
            if (this.penguin.hasEgg()) {
                return true;
            }
            return true;
        }

        @Override
        public void start() {
            this.penguin.setLandBound(true);
        }

        @Override
        public void stop() {
            this.penguin.setLandBound(false);
        }

        @Override
        public boolean shouldContinue() {
            return true;
        }

        public static class TravelGoal extends Goal {
            private final PenguinEntity penguin;
            private final double speed;
            private boolean noPath;

            TravelGoal(PenguinEntity penguin, double speed) {
                this.penguin = penguin;
                this.speed = speed;
            }

            @Override
            public boolean canStart() {
                return !this.penguin.isLandBound() && !this.penguin.hasEgg() && this.penguin.isTouchingWater();
            }

            @Override
            public void start() {
                Random random = this.penguin.random;
                int k = random.nextInt(1025) - 512;
                int l = random.nextInt(9) - 4;
                int m = random.nextInt(1025) - 512;
                if ((double) l + this.penguin.getY() > (double) (this.penguin.getWorld().getSeaLevel() - 1)) {
                    l = 0;
                }
                BlockPos blockPos = BlockPos.ofFloored((double) k + this.penguin.getX(), (double) l + this.penguin.getY(), (double) m + this.penguin.getZ());
                this.penguin.setTravelPos(blockPos);
                this.penguin.setActivelyTraveling(true);
                this.noPath = false;
            }

            @Deprecated
            @Override
            public void tick() {
                if (this.penguin.getNavigation().isIdle()) {
                    Vec3d vec3d = Vec3d.ofBottomCenter(this.penguin.getTravelPos());
                    Vec3d vec3d2 = NoPenaltyTargeting.findTo(this.penguin, 16, 3, vec3d, 0.3141592741012573);
                    if (vec3d2 == null) {
                        vec3d2 = NoPenaltyTargeting.findTo(this.penguin, 8, 7, vec3d, 1.5707963705062866);
                    }
                    if (vec3d2 != null) {
                        int i = MathHelper.floor(vec3d2.x);
                        int j = MathHelper.floor(vec3d2.z);
                        if (!this.penguin.getWorld().isRegionLoaded(i - 34, j - 34, i + 34, j + 34)) {
                            vec3d2 = null;
                        }
                    }
                    if (vec3d2 == null) {
                        this.noPath = true;
                        return;
                    }
                    this.penguin.getNavigation().startMovingTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);
                }
            }

            @Override
            public boolean shouldContinue() {
                return !this.penguin.getNavigation().isIdle() && !this.noPath && !this.penguin.isLandBound() && !this.penguin.isInLove() && !this.penguin.hasEgg();
            }

            @Override
            public void stop() {
                this.penguin.setActivelyTraveling(false);
                super.stop();
            }
        }

        public static class WanderOnLandGoal extends WanderAroundGoal {
            private final PenguinEntity penguin;

            WanderOnLandGoal(PenguinEntity penguin, double speed, int chance) {
                super(penguin, speed, chance);
                this.penguin = penguin;
            }

            @Override
            public boolean canStart() {
                if (!(this.mob.isTouchingWater() || this.penguin.isLandBound() || this.penguin.hasEgg())) {
                    return super.canStart();
                }
                return false;
            }
        }

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
    }
}
