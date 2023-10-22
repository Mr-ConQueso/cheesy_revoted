package net.mrconqueso.cheesy_revoted.entity.goals.penguin;

import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.mrconqueso.cheesy_revoted.entity.custom.PenguinEntity;

public class PenguinTravelGoal extends Goal {
    private final Random random = Random.create();
    private final PenguinEntity penguin;
    private final double speed;
    private boolean noPath;

    public PenguinTravelGoal(PenguinEntity penguin, double speed) {
        this.penguin = penguin;
        this.speed = speed;
    }

    @Override
    public boolean canStart() {
        return !this.penguin.isLandBound() && !this.penguin.hasEgg() && this.penguin.isTouchingWater();
    }

    @Override
    public void start() {
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
