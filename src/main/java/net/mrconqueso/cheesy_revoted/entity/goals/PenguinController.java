package net.mrconqueso.cheesy_revoted.entity.goals;

import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.AmphibiousSwimNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

// --------- / PENGUIN AI CONTROLLER / --------- //
public class PenguinController extends AnimalEntity {
    private static final TrackedData<BlockPos> TRAVEL_POS = DataTracker.registerData(PenguinController.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private static final TrackedData<Boolean> LAND_BOUND = DataTracker.registerData(PenguinController.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> ACTIVELY_TRAVELING = DataTracker.registerData(PenguinController.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.SALMON);

    protected PenguinController(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.setPathfindingPenalty(PathNodeType.DOOR_IRON_CLOSED, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DOOR_WOOD_CLOSED, -1.0f);
        this.setPathfindingPenalty(PathNodeType.DOOR_OPEN, -1.0f);
        this.moveControl = new PenguinMoveControl(this);
        this.setStepHeight(1.0f);
    }

    public boolean isLandBound() {
        return this.dataTracker.get(LAND_BOUND);
    }
    void setTravelPos(BlockPos pos) { this.dataTracker.set(TRAVEL_POS, pos); }
    BlockPos getTravelPos() {
        return this.dataTracker.get(TRAVEL_POS);
    }
    void setActivelyTraveling(boolean traveling) {
        this.dataTracker.set(ACTIVELY_TRAVELING, traveling);
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(LAND_BOUND, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new PenguinEscapeDangerGoal(this, 1.2));
        this.goalSelector.add(2, new TemptGoal(this, 1.1, BREEDING_INGREDIENT, false));
        this.goalSelector.add(3, new WanderInWaterGoal(this, 1.0));
        this.goalSelector.add(7, new TravelGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(9, new WanderOnLandGoal(this, 1.0, 100));
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
    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        if (isSwimming()) {
            return dimensions.height * 0.70f;
        }
        return dimensions.height * 0.65f;
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new PenguinSwimNavigation(this, world);
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

    static class PenguinMoveControl extends MoveControl {
        private final PenguinController turtle;

        PenguinMoveControl(PenguinController turtle) {
            super(turtle);
            this.turtle = turtle;
        }

        private void updateVelocity() {
            if (this.turtle.isTouchingWater()) {
                this.turtle.setVelocity(this.turtle.getVelocity().add(0.0, 0.005, 0.0));
                if (this.turtle.isBaby()) {
                    this.turtle.setMovementSpeed(Math.max(this.turtle.getMovementSpeed() / 3.0f, 0.06f));
                }
            } else if (this.turtle.isOnGround()) {
                this.turtle.setMovementSpeed(Math.max(this.turtle.getMovementSpeed() / 2.0f, 0.06f));
            }
        }

        @Override
        public void tick() {
            double f;
            double e;
            this.updateVelocity();
            if (this.state != MoveControl.State.MOVE_TO || this.turtle.getNavigation().isIdle()) {
                this.turtle.setMovementSpeed(0.0f);
                return;
            }
            double d = this.targetX - this.turtle.getX();
            double g = Math.sqrt(d * d + (e = this.targetY - this.turtle.getY()) * e + (f = this.targetZ - this.turtle.getZ()) * f);
            if (g < (double)1.0E-5f) {
                this.entity.setMovementSpeed(0.0f);
                return;
            }
            e /= g;
            float h = (float)(MathHelper.atan2(f, d) * 57.2957763671875) - 90.0f;
            this.turtle.setYaw(this.wrapDegrees(this.turtle.getYaw(), h, 90.0f));
            this.turtle.bodyYaw = this.turtle.getYaw();
            float i = (float)(this.speed * this.turtle.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
            this.turtle.setMovementSpeed(MathHelper.lerp(0.125f, this.turtle.getMovementSpeed(), i));
            this.turtle.setVelocity(this.turtle.getVelocity().add(0.0, (double)this.turtle.getMovementSpeed() * e * 0.1, 0.0));
        }
    }

    static class PenguinEscapeDangerGoal extends EscapeDangerGoal {
        PenguinEscapeDangerGoal(PenguinController turtle, double speed) {
            super(turtle, speed);
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

    static class WanderInWaterGoal extends MoveToTargetPosGoal {
        private final PenguinController turtle;
        WanderInWaterGoal(PenguinController turtle, double speed) {
            super(turtle, turtle.isBaby() ? 2.0 : speed, 24);
            this.turtle = turtle;
            this.lowestY = -1;
        }
        @Override
        public boolean shouldContinue() {
            return !this.turtle.isTouchingWater() && this.tryingTime <= 1200 && this.isTargetPos(this.turtle.getWorld(), this.targetPos);
        }
        @Override
        public boolean canStart() {
            if (this.turtle.isBaby() && !this.turtle.isTouchingWater()) {
                return super.canStart();
            }
            if (!(this.turtle.isLandBound() || this.turtle.isTouchingWater())) {
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

    static class TravelGoal extends Goal {
        private final PenguinController turtle;
        private final double speed;
        private boolean noPath;

        TravelGoal(PenguinController turtle, double speed) {
            this.turtle = turtle;
            this.speed = speed;
        }

        @Override
        public boolean canStart() {
            return !this.turtle.isLandBound() && this.turtle.isTouchingWater();
        }

        @Override
        public void start() {
            Random random = this.turtle.random;
            int k = random.nextInt(1025) - 512;
            int l = random.nextInt(9) - 4;
            int m = random.nextInt(1025) - 512;
            if ((double)l + this.turtle.getY() > (double)(this.turtle.getWorld().getSeaLevel() - 1)) {
                l = 0;
            }
            BlockPos blockPos = BlockPos.ofFloored((double)k + this.turtle.getX(), (double)l + this.turtle.getY(), (double)m + this.turtle.getZ());
            this.turtle.setTravelPos(blockPos);
            this.turtle.setActivelyTraveling(true);
            this.noPath = false;
        }

        @Deprecated
        @Override
        public void tick() {
            if (this.turtle.getNavigation().isIdle()) {
                Vec3d vec3d = Vec3d.ofBottomCenter(this.turtle.getTravelPos());
                Vec3d vec3d2 = NoPenaltyTargeting.findTo(this.turtle, 16, 3, vec3d, 0.3141592741012573);
                if (vec3d2 == null) {
                    vec3d2 = NoPenaltyTargeting.findTo(this.turtle, 8, 7, vec3d, 1.5707963705062866);
                }
                if (vec3d2 != null) {
                    int i = MathHelper.floor(vec3d2.x);
                    int j = MathHelper.floor(vec3d2.z);
                    if (!this.turtle.getWorld().isRegionLoaded(i - 34, j - 34, i + 34, j + 34)) {
                        vec3d2 = null;
                    }
                }
                if (vec3d2 == null) {
                    this.noPath = true;
                    return;
                }
                this.turtle.getNavigation().startMovingTo(vec3d2.x, vec3d2.y, vec3d2.z, this.speed);
            }
        }

        @Override
        public boolean shouldContinue() {
            return !this.turtle.getNavigation().isIdle() && !this.noPath && !this.turtle.isLandBound() && !this.turtle.isInLove();
        }

        @Override
        public void stop() {
            this.turtle.setActivelyTraveling(false);
            super.stop();
        }
    }

    static class WanderOnLandGoal extends WanderAroundGoal {
        private final PenguinController turtle;

        WanderOnLandGoal(PenguinController turtle, double speed, int chance) {
            super(turtle, speed, chance);
            this.turtle = turtle;
        }

        @Override
        public boolean canStart() {
            if (!(this.mob.isTouchingWater() || this.turtle.isLandBound())) {
                return super.canStart();
            }
            return false;
        }
    }

    static class PenguinSwimNavigation extends AmphibiousSwimNavigation {
        PenguinSwimNavigation(PenguinController owner, World world) {
            super(owner, world);
        }
        @Override
        public boolean isValidPosition(BlockPos pos) {
            if (this.entity instanceof TurtleEntity) {
                return this.world.getBlockState(pos).isOf(Blocks.WATER);
            }
            return !this.world.getBlockState(pos.down()).isAir();
        }
    }
}
