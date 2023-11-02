package net.mrconqueso.cheesy_revoted.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SuspiciousStewIngredient;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.mrconqueso.cheesy_revoted.registry.ModEntities;
import net.mrconqueso.cheesy_revoted.entity.variants.MoobloomVariant;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;

import java.util.List;
import java.util.Optional;

public class MoobloomEntity extends AnimalEntity implements GeoEntity {
    public MoobloomEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    // --------- / VARIABLES / --------- //
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.WHEAT);

    // --------- / ATTRIBUTES & AI / --------- //
    public static DefaultAttributeContainer.Builder setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }
    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 2.0));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.25, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    // --------- / CHILD / --------- //
    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        MoobloomEntity moobloomEntity = ModEntities.MOOBLOOM.create(world);
        if (moobloomEntity != null) {
            moobloomEntity.setVariant(getChildColor(this, (MoobloomEntity) entity));
        }
        return moobloomEntity;
    }

    private MoobloomVariant getChildColor(AnimalEntity firstParent, AnimalEntity secondParent) {
        MoobloomVariant variant1 = ((MoobloomEntity)firstParent).getVariant();
        MoobloomVariant variant2 = ((MoobloomEntity)secondParent).getVariant();

        if ((variant1 == MoobloomVariant.RED && variant2 == MoobloomVariant.DEFAULT) || (variant1 == MoobloomVariant.DEFAULT && variant2 == MoobloomVariant.RED)) {
            return MoobloomVariant.ORANGE;

        } else if ((variant1 == MoobloomVariant.RED && variant2 == MoobloomVariant.BLUE) || (variant1 == MoobloomVariant.BLUE && variant2 == MoobloomVariant.RED)) {
            return MoobloomVariant.PURPLE;

        } else if ((variant1 == MoobloomVariant.RED && variant2 == MoobloomVariant.WHITE) || (variant1 == MoobloomVariant.WHITE && variant2 == MoobloomVariant.RED)) {
            return MoobloomVariant.PINK;

        } else if ((variant1 == MoobloomVariant.DEFAULT && variant2 == MoobloomVariant.BLUE) || (variant1 == MoobloomVariant.BLUE && variant2 == MoobloomVariant.DEFAULT)) {
            return MoobloomVariant.GREEN;

        } else if ((variant1 == MoobloomVariant.DEFAULT && variant2 == MoobloomVariant.WHITE) || (variant1 == MoobloomVariant.WHITE && variant2 == MoobloomVariant.DEFAULT)) {
            return MoobloomVariant.LIME;

        } else if ((variant1 == MoobloomVariant.LIGHT_BLUE && variant2 == MoobloomVariant.GREEN) || (variant1 == MoobloomVariant.GREEN && variant2 == MoobloomVariant.LIGHT_BLUE)) {
            return MoobloomVariant.CYAN;

        } else if ((variant1 == MoobloomVariant.BLUE && variant2 == MoobloomVariant.WHITE) || (variant1 == MoobloomVariant.WHITE && variant2 == MoobloomVariant.BLUE)) {
            return MoobloomVariant.LIGHT_BLUE;

        } else if (variant1 == MoobloomVariant.PURPLE && variant2 == MoobloomVariant.WHITE || (variant1 == MoobloomVariant.WHITE && variant2 == MoobloomVariant.PURPLE)) {
            return MoobloomVariant.MAGENTA;

        } else if ((variant1 == MoobloomVariant.BLACK && variant2 == MoobloomVariant.WHITE) || (variant1 == MoobloomVariant.WHITE && variant2 == MoobloomVariant.BLACK)) {
            return MoobloomVariant.GRAY;

        } else if ((variant1 == MoobloomVariant.WHITE && variant2 == MoobloomVariant.GRAY) || (variant1 == MoobloomVariant.GRAY && variant2 == MoobloomVariant.WHITE)) {
            return MoobloomVariant.LIGHT_GRAY;

        } else if ((variant1 == MoobloomVariant.ORANGE && variant2 == MoobloomVariant.BLACK) || (variant1 == MoobloomVariant.BLACK && variant2 == MoobloomVariant.ORANGE)) {
            return MoobloomVariant.BROWN;

        }
        return this.getVariant();
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

        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.isOf(Items.SHEARS)) {
            this.sheared(SoundCategory.PLAYERS);
            this.emitGameEvent(GameEvent.SHEAR, player);
            if (!this.getWorld().isClient) {
                itemStack.damage(1, player, playerx -> playerx.sendToolBreakStatus(hand));
            }
            return ActionResult.success(this.getWorld().isClient);
        }

        return super.interactMob(player, hand);
    }

    public void sheared(SoundCategory shearedSoundCategory) {
        CowEntity cowEntity;
        this.getWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, shearedSoundCategory, 1.0f, 1.0f);

        if (!this.getWorld().isClient() && (cowEntity = EntityType.COW.create(this.getWorld())) != null) {

            ((ServerWorld)this.getWorld()).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            this.discard();
            cowEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            cowEntity.setHealth(this.getHealth());
            cowEntity.bodyYaw = this.bodyYaw;
            if (this.hasCustomName()) {
                cowEntity.setCustomName(this.getCustomName());
                cowEntity.setCustomNameVisible(this.isCustomNameVisible());
            }
            if (this.isPersistent()) {
                cowEntity.setPersistent();
            }
            cowEntity.setInvulnerable(this.isInvulnerable());
            this.getWorld().spawnEntity(cowEntity);
            for (int i = 0; i < 5; ++i) {
                this.getWorld().spawnEntity(new ItemEntity(this.getWorld(), this.getX(), this.getBodyY(1.0), this.getZ(), new ItemStack(this.getVariantFlower())));
            }
        }
    }
    @Override
    public boolean isBreedingItem(ItemStack stack) { return BREEDING_INGREDIENT.test(stack); }

    // --------- / SOUNDS / --------- //
    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_COW_AMBIENT; }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) { return SoundEvents.ENTITY_COW_HURT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_COW_DEATH; }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15f, 1.0f);
    }

    // --------- / NBT-DATA / --------- //
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
    }

    // --------- / VARIANTS / --------- //
    private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
            DataTracker.registerData(MoobloomEntity.class, TrackedDataHandlerRegistry.INTEGER);

    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        MoobloomVariant variant = Util.getRandom(MoobloomVariant.values(), this.random);
        setVariant(variant);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public MoobloomVariant getVariant() {
        return MoobloomVariant.byId(this.getTypeVariant() & 255);
    }

    public Block getVariantFlower() {
        return MoobloomVariant.getFlowerDrop(this.getTypeVariant());
    }

    private int getTypeVariant() {
        return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
    }

    private void setVariant(MoobloomVariant variant) {
        this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
    }

    // --------- / MODEL SETTINGS / --------- //
    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) { return dimensions.height * 0.928f; }

    @Override
    protected Vector3f getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        return new Vector3f(0.0f, dimensions.height - 0.03125f * scaleFactor, 0.0f);
    }

    @Override
    protected Vec3d getLeashOffset() { return new Vec3d(0.0, 0.6f * this.getStandingEyeHeight(), this.getWidth() * 0.4f); }
}
