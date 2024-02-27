package net.mrconqueso.cheesy_revoted.entity;


import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.FuzzyTargeting;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.mrconqueso.cheesy_revoted.entity.ai.SnifferBrainRevoted;
import net.mrconqueso.cheesy_revoted.registry.ModBlocks;
import net.mrconqueso.cheesy_revoted.registry.ModEntities;
import net.mrconqueso.cheesy_revoted.registry.ModTags;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SnifferEntityRevoted extends AnimalEntity implements GeoEntity {

    // --------- / VARIABLES / --------- //
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final EntityDimensions layingEntityDimensions = EntityDimensions.changing(ModEntities.SNIFFER.getWidth(), ModEntities.SNIFFER.getWidth() - 0.4f);
    private static final TrackedData<SnifferEntityRevoted.State> STATE = DataTracker.registerData(SnifferEntityRevoted.class, TrackedDataHandlerRegistry.SNIFFER_STATE);
    private static final TrackedData<Integer> FINISH_DIG_TIME = DataTracker.registerData(SnifferEntityRevoted.class, TrackedDataHandlerRegistry.INTEGER);

    public SnifferEntityRevoted(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.dataTracker.startTracking(STATE, State.IDLING);
        this.dataTracker.startTracking(FINISH_DIG_TIME, 0);
        this.getNavigation().setCanSwim(true);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DANGER_POWDER_SNOW, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DAMAGE_CAUTIOUS, -1.0f);
    }


    // --------- / ATTRIBUTES & AI / --------- //
    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 14.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1f);
    }

    // --------- / ADVANCED AI / --------- //
    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return SnifferBrainRevoted.create(this.createBrainProfile().deserialize(dynamic));
    }

    public Brain<SnifferEntityRevoted> getBrain() {
        return super.getBrain();
    }

    protected Brain.Profile<SnifferEntityRevoted> createBrainProfile() {
        return Brain.createProfile(SnifferBrainRevoted.MEMORY_MODULES, SnifferBrainRevoted.SENSORS);
    }

    @Override
    protected void mobTick() {
        this.getWorld().getProfiler().push("snifferBrain");
        this.getBrain().tick((ServerWorld) this.getWorld(), this);
        this.getWorld().getProfiler().swap("snifferActivityUpdate");
        SnifferBrainRevoted.updateActivities(this);
        this.getWorld().getProfiler().pop();
        super.mobTick();
    }

    @Override
    protected void sendAiDebugData() {
        super.sendAiDebugData();
        DebugInfoSender.sendBrainDebugData(this);
    }

    @Override
    public void tick() {
        switch (this.getState()) {
            case DIGGING: {
                this.spawnDiggingParticles(this.diggingAnimationState).dropSeeds();
                break;
            }
            case SEARCHING: {
                this.playSearchingSound();
            }
        }
        super.tick();
    }

    @Override
    public void onStartPathfinding() {
        super.onStartPathfinding();
        if (this.isOnFire() || this.isTouchingWater()) {
            this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        }
    }

    @Override
    public void onFinishPathfinding() {
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
    }

    private SnifferEntityRevoted setDigging() {
        this.dataTracker.set(FINISH_DIG_TIME, this.age + 120);
        this.getWorld().sendEntityStatus(this, EntityStatuses.START_DIGGING);
        return this;
    }

    public SnifferEntityRevoted finishDigging(boolean explored) {
        if (explored) {
            this.addExploredPosition(this.getSteppingPos());
        }
        return this;
    }

    Optional<BlockPos> findSniffingTargetPos() {
        return IntStream.range(0, 5).mapToObj(i -> FuzzyTargeting.find(this, 10 + 2 * i, 3)).filter(Objects::nonNull).map(BlockPos::ofFloored).filter(pos -> this.getWorld().getWorldBorder().contains((BlockPos)pos)).map(BlockPos::down).filter(this::isDiggable).findFirst();
    }

    boolean canDig() {
        return !this.isPanicking() && !this.isTempted() && !this.isBaby() && !this.isTouchingWater() && this.isOnGround() && !this.hasVehicle() && this.isDiggable(this.getDigPos().down());
    }

    private boolean isDiggable(BlockPos pos) {
        return this.getWorld().getBlockState(pos).isIn(ModTags.Blocks.SNIFFER_DIGGABLE_BLOCK) && this.getExploredPositions().noneMatch(globalPos -> GlobalPos.create(this.getWorld().getRegistryKey(), pos).equals(globalPos)) && Optional.ofNullable(this.getNavigation().findPathTo(pos, 1)).map(Path::reachesTarget).orElse(false) != false;
    }

    private void dropSeeds() {
        if (this.getWorld().isClient() || this.dataTracker.get(FINISH_DIG_TIME) != this.age) {
            return;
        }
        ServerWorld serverWorld = (ServerWorld)this.getWorld();
        LootTable lootTable = serverWorld.getServer().getLootManager().getLootTable(LootTables.SNIFFER_DIGGING_GAMEPLAY);
        LootContextParameterSet lootContextParameterSet = new LootContextParameterSet.Builder(serverWorld).add(LootContextParameters.ORIGIN, this.getDigLocation()).add(LootContextParameters.THIS_ENTITY, this).build(LootContextTypes.GIFT);
        ObjectArrayList<ItemStack> list = lootTable.generateLoot(lootContextParameterSet);
        BlockPos blockPos = this.getDigPos();
        for (ItemStack itemStack : list) {
            ItemEntity itemEntity = new ItemEntity(serverWorld, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);
            itemEntity.setToDefaultPickupDelay();
            serverWorld.spawnEntity(itemEntity);
        }
        this.playSound(SoundEvents.ENTITY_SNIFFER_DROP_SEED, 1.0f, 1.0f);
    }

    private SnifferEntityRevoted spawnDiggingParticles(AnimationState diggingAnimationState) {
        boolean bl;
        boolean bl2 = bl = diggingAnimationState.getTimeRunning() > 1700L && diggingAnimationState.getTimeRunning() < 6000L;
        if (bl) {
            BlockPos blockPos = this.getDigPos();
            BlockState blockState = this.getWorld().getBlockState(blockPos.down());
            if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
                for (int i = 0; i < 30; ++i) {
                    Vec3d vec3d = Vec3d.ofCenter(blockPos).add(0.0, -0.65f, 0.0);
                    this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
                }
                if (this.age % 10 == 0) {
                    this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), blockState.getSoundGroup().getHitSound(), this.getSoundCategory(), 0.5f, 0.5f, false);
                }
            }
        }
        if (this.age % 10 == 0) {
            this.getWorld().emitGameEvent(GameEvent.ENTITY_ACTION, this.getDigPos(), GameEvent.Emitter.of(this));
        }
        return this;
    }

    private SnifferEntityRevoted addExploredPosition(BlockPos pos) {
        List list = this.getExploredPositions().limit(20L).collect(Collectors.toList());
        list.add(0, GlobalPos.create(this.getWorld().getRegistryKey(), pos));
        this.getBrain().remember(MemoryModuleType.SNIFFER_EXPLORED_POSITIONS, list);
        return this;
    }

    private Stream<GlobalPos> getExploredPositions() {
        return this.getBrain().getOptionalRegisteredMemory(MemoryModuleType.SNIFFER_EXPLORED_POSITIONS).stream().flatMap(Collection::stream);
    }

    @Override
    protected void jump() {
        double e;
        super.jump();
        double d = this.moveControl.getSpeed();
        if (d > 0.0 && (e = this.getVelocity().horizontalLengthSquared()) < 0.01) {
            this.updateVelocity(0.1f, new Vec3d(0.0, 0.0, 1.0));
        }
    }

    // --------- / CHILD / --------- //
    @Override
    public void setBaby(boolean baby) {
        this.setBreedingAge(baby ? -48000 : 0);
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.SNIFFER.create(world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTags.SNIFFER_FOOD);
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        if (other instanceof SnifferEntityRevoted) {
            SnifferEntityRevoted snifferEntity = (SnifferEntityRevoted)other;
            Set<State> set = Set.of(SnifferEntityRevoted.State.IDLING, SnifferEntityRevoted.State.SCENTING, SnifferEntityRevoted.State.FEELING_HAPPY);
            return set.contains((Object)this.getState()) && set.contains((Object)snifferEntity.getState()) && super.canBreedWith(other);
        }
        return false;
    }

    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        ItemStack itemStack = new ItemStack(ModBlocks.SNIFFER_EGG);
        ItemEntity itemEntity = new ItemEntity(world, this.getPos().getX(), this.getPos().getY(), this.getPos().getZ(), itemStack);
        itemEntity.setToDefaultPickupDelay();
        this.breed(world, other, null);
        this.playSound(SoundEvents.BLOCK_SNIFFER_EGG_PLOP, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 0.5f);
        world.spawnEntity(itemEntity);
    }

    // --------- / ANIMATIONS / --------- //

    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("misc.idle");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("move.walk");
    public static final RawAnimation SCENTING = RawAnimation.begin().thenLoop("misc.scenting");
    public static final RawAnimation SNIFFING = RawAnimation.begin().thenLoop("misc.sniffing");
    public static final RawAnimation DIGGING = RawAnimation.begin().thenLoop("misc.digging");
    public static final RawAnimation RISING = RawAnimation.begin().thenLoop("move.rising");
    public static final RawAnimation FEELING_HAPPY = RawAnimation.begin().thenLoop("misc.feeling_happy");

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (STATE.equals(data)) {
            SnifferEntityRevoted.State state = this.getState();
            this.stopAnimations();
            switch (state) {
                case SCENTING: {
                    this.scentingAnimationState.startIfNotRunning(this.age);
                    break;
                }
                case SNIFFING: {
                    this.sniffingAnimationState.startIfNotRunning(this.age);
                    break;
                }
                case DIGGING: {
                    this.diggingAnimationState.startIfNotRunning(this.age);
                    break;
                }
                case RISING: {
                    this.risingAnimationState.startIfNotRunning(this.age);
                    break;
                }
                case FEELING_HAPPY: {
                    this.feelingHappyAnimationState.startIfNotRunning(this.age);
                }
            }
            this.calculateDimensions();
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(crabAnimationController(this));
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public <T extends Entity & GeoAnimatable> AnimationController<T> crabAnimationController(T entity) {
        return new AnimationController<T>(entity, "Walk/Idle", 1, state -> {
            return state.setAndContinue(state.isMoving() ? WALK : IDLE);
        });
    }

    private boolean isMoving() {
        if (this.getMovementSpeed() > 0) { return true; }
        return false;
    }

    // --------- / INTERACTIONS / --------- //
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        boolean bl = this.isBreedingItem(itemStack);
        ActionResult actionResult = super.interactMob(player, hand);
        if (actionResult.isAccepted() && bl) {
            this.getWorld().playSoundFromEntity(null, this, this.getEatSound(itemStack), SoundCategory.NEUTRAL, 1.0f, MathHelper.nextBetween(this.getWorld().random, 0.8f, 1.2f));
        }
        return actionResult;
    }

    // --------- / SOUNDS / --------- //
    private SnifferEntityRevoted playScentingSound() {
        this.playSound(SoundEvents.ENTITY_SNIFFER_SCENTING, 1.0f, this.isBaby() ? 1.3f : 1.0f);
        return this;
    }

    private void playSearchingSound() {
        if (this.getWorld().isClient() && this.age % 20 == 0) {
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_SNIFFER_SEARCHING, this.getSoundCategory(), 1.0f, 1.0f, false);
        }
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SNIFFER_STEP, 0.15f, 1.0f);
    }

    @Override
    public SoundEvent getEatSound(ItemStack stack) {
        return SoundEvents.ENTITY_SNIFFER_EAT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return Set.of(SnifferEntityRevoted.State.DIGGING, SnifferEntityRevoted.State.SEARCHING).contains((Object)this.getState()) ? null : SoundEvents.ENTITY_SNIFFER_IDLE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SNIFFER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SNIFFER_DEATH;
    }

    // --------- / NBT-DATA / --------- //
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    public boolean isSearching() {
        return this.getState() == SnifferEntityRevoted.State.SEARCHING;
    }

    public boolean isTempted() {
        return this.brain.getOptionalRegisteredMemory(MemoryModuleType.IS_TEMPTED).orElse(false);
    }

    public boolean canTryToDig() {
        return !this.isTempted() && !this.isPanicking() && !this.isTouchingWater() && !this.isInLove() && this.isOnGround() && !this.hasVehicle() && !this.isLeashed();
    }

    public boolean isDiggingOrSearching() {
        return this.getState() == SnifferEntityRevoted.State.DIGGING || this.getState() == SnifferEntityRevoted.State.SEARCHING;
    }

    private BlockPos getDigPos() {
        Vec3d vec3d = this.getDigLocation();
        return BlockPos.ofFloored(vec3d.getX(), this.getY() + (double)0.2f, vec3d.getZ());
    }

    private Vec3d getDigLocation() {
        return this.getPos().add(this.getRotationVecClient().multiply(2.25));
    }

    private SnifferEntityRevoted.State getState() {
        return this.dataTracker.get(STATE);
    }

    private SnifferEntityRevoted setState(SnifferEntityRevoted.State state) {
        this.dataTracker.set(STATE, state);
        return this;
    }

    // --------- / ENTITY SETTINGS / --------- //
    @Override
    public void onDeath(DamageSource damageSource) {
        this.startState(SnifferEntityRevoted.State.IDLING);
        super.onDeath(damageSource);
    }

    // --------- / MODEL SETTINGS / --------- //

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.getDimensions((EntityPose)pose).height * 0.6f;
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        if (this.dataTracker.containsKey(STATE) && this.getState() == SnifferEntityRevoted.State.DIGGING) {
            return layingEntityDimensions.scaled(this.getScaleFactor());
        }
        return super.getDimensions(pose);
    }

    @Override
    protected Vector3f getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return new Vector3f(0.0f, dimensions.height + 0.34375f * scaleFactor, 0.0f);
    }

    @Override
    public float getNameLabelHeight() {
        return super.getNameLabelHeight() + 0.3f;
    }

    @Override
    public int getMaxHeadRotation() {
        return 50;
    }

    @Override
    public Box getVisibilityBoundingBox() {
        return super.getVisibilityBoundingBox().expand(0.6f);
    }

    // --------- / STATES / --------- //
    public static enum State {
        IDLING,
        FEELING_HAPPY,
        SCENTING,
        SNIFFING,
        SEARCHING,
        DIGGING,
        RISING;
    }

    public SnifferEntityRevoted startState(SnifferEntityRevoted.State state) {
        switch (state) {
            case IDLING: {
                this.setState(SnifferEntityRevoted.State.IDLING);
                break;
            }
            case SCENTING: {
                this.setState(SnifferEntityRevoted.State.SCENTING).playScentingSound();
                break;
            }
            case SNIFFING: {
                this.playSound(SoundEvents.ENTITY_SNIFFER_SNIFFING, 1.0f, 1.0f);
                this.setState(SnifferEntityRevoted.State.SNIFFING);
                break;
            }
            case SEARCHING: {
                this.setState(SnifferEntityRevoted.State.SEARCHING);
                break;
            }
            case DIGGING: {
                this.setState(SnifferEntityRevoted.State.DIGGING).setDigging();
                break;
            }
            case RISING: {
                this.playSound(SoundEvents.ENTITY_SNIFFER_DIGGING_STOP, 1.0f, 1.0f);
                this.setState(SnifferEntityRevoted.State.RISING);
                break;
            }
            case FEELING_HAPPY: {
                this.playSound(SoundEvents.ENTITY_SNIFFER_HAPPY, 1.0f, 1.0f);
                this.setState(SnifferEntityRevoted.State.FEELING_HAPPY);
            }
        }
        return this;
    }
}

