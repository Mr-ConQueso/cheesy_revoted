package net.mrconqueso.cheesy_revoted.entity.custom;

import net.minecraft.block.BlockState;
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
import net.minecraft.world.World;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class CrabEntity extends AnimalEntity implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.MANGROVE_PROPAGULE, Items.SEAGRASS);
    public CrabEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    private boolean songPlaying;
    @Nullable
    private BlockPos songSource;

    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.15f);
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new FollowParentGoal(this, 1.25));
        this.goalSelector.add(4, new TemptGoal(this, 1.2, BREEDING_INGREDIENT, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return ModEntities.CRAB.create(world);
    }

    /*
    @Override
    public void tickMovement() {
        if (this.songSource == null || !this.songSource.isWithinDistance(this.getPos(), 10) || !this.getWorld().getBlockState(this.songSource).isOf(Blocks.JUKEBOX)) {
            this.songPlaying = false;
            this.songSource = null;
        }
        super.tickMovement();

        this.songPlaying = true;
    }

    @Override
    public void setNearbySongPlaying(BlockPos songPosition, boolean playing) {
        this.songSource = songPosition;
        this.songPlaying = playing;
    }

    public boolean isSongPlaying() {
        return this.songPlaying;
    }
    */

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 0, this::predicate));
        //controllers.add(DefaultAnimations.genericWalkIdleController(this));
        //controllers.add(DefaultAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_STRIKE));
    }

    private PlayState predicate(AnimationState<GeoAnimatable> geoAnimatableAnimationState) {
        /*
        if (isSongPlaying()) {
            int randomDance = (int) (Math.random() * (2 - 0));
            switch(randomDance) {
                case 0:
                    geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("dance.clap", Animation.LoopType.LOOP));
                    break;
                case 1:
                    geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("dance.touchfloor", Animation.LoopType.LOOP));
                    break;
                case 2:
                    geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("dance.sidewave", Animation.LoopType.LOOP));
                    break;
                default:
                    geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("dance.clap", Animation.LoopType.LOOP));
                    break;
            }
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("dance.clap", Animation.LoopType.LOOP));
        }

        if (geoAnimatableAnimationState.isMoving() && !isSongPlaying()) {
            geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("move.walk", Animation.LoopType.LOOP));
        }

         */

        geoAnimatableAnimationState.getController().setAnimation(RawAnimation.begin().then("misc.idle", Animation.LoopType.LOOP));
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
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_PIG_AMBIENT; }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return SoundEvents.ENTITY_PIG_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_PIG_DEATH; }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f);
    }

    @Override
    protected Vec3d getLeashOffset() {
        return super.getLeashOffset();
    }
}
