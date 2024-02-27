package net.mrconqueso.cheesy_revoted.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
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
import net.mrconqueso.cheesy_revoted.registry.ModEntities;
import net.mrconqueso.cheesy_revoted.registry.ModItems;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;

import java.util.Random;

public class RascalEntity extends AnimalEntity implements GeoEntity {
    private int danceType;
    public RascalEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.danceType = new Random().nextInt(3);
    }

    // --------- / VARIABLES / --------- //
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.MANGROVE_PROPAGULE, Items.SEAGRASS);
    private boolean songPlaying;
    @Nullable
    private BlockPos songSource;

    // --------- / ATTRIBUTES & AI / --------- //
    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 7.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15f);
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(2, new FollowParentGoal(this, 1.25));
        this.goalSelector.add(3, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
    }

    // --------- / CHILD / --------- //
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.CRAB.create(world);
    }

    // --------- / DANCE / --------- //

    @Override
    public void tickMovement() {
        if (this.songSource == null || !this.songSource.isWithinDistance(this.getPos(), 10) || !this.getWorld().getBlockState(this.songSource).isOf(Blocks.JUKEBOX)) {
            this.songPlaying = false;
            this.songSource = null;
        }
        super.tickMovement();
    }

    @Override
    public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
        this.songSource = songPosition;
        this.songPlaying = playing;
    }

    public boolean isSongPlaying() {
        return this.songPlaying;
    }
    public int getDanceType() { return  this.danceType; }

    // --------- / ANIMATIONS / --------- //

    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("misc.idle");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("move.walk");
    public static final RawAnimation ATTACK = RawAnimation.begin().thenLoop("attack.pinch");
    public static final RawAnimation[] DANCE = {
            RawAnimation.begin().thenLoop("dance.clap"),
            RawAnimation.begin().thenLoop("dance.touchfloor"),
            RawAnimation.begin().thenLoop("dance.sidewave")
    };
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
            if (this.isSongPlaying()) {
                return state.setAndContinue(DANCE[getDanceType()]);
            }
            else {
                return state.setAndContinue(state.isMoving() ? WALK : IDLE);
            }
        });
    }

    private boolean isMoving() {
        if (this.getMovementSpeed() > 0) { return true; }
        return false;
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
    protected void playStepSound(BlockPos pos, BlockState state) { this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f); }

    // --------- / ENTITY SETTINGS / --------- //
    @Override
    public boolean canBreatheInWater() {
        return true;
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    // --------- / MODEL SETTINGS / --------- //
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) { return dimensions.height * 0.555f; }

    @Override
    protected Vector3f getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return new Vector3f(0.0f, dimensions.height - 0.23f * scaleFactor, 0.0f);
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        CreeperEntity creeperEntity;
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        Entity entity = source.getAttacker();
        if (entity instanceof CreeperEntity && (creeperEntity = (CreeperEntity)entity).shouldDropHead()) {
            creeperEntity.onHeadDropped();
            this.dropItem(ModItems.MUSIC_DISC_CRAB_RAVE);
        }
    }
}
