package net.mrconqueso.cheesy_revoted.entity.goals.penguin;

import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.mrconqueso.cheesy_revoted.entity.PenguinEntity;

public class PenguinWanderOnLandGoal extends WanderAroundGoal {
    private final PenguinEntity penguin;

    public PenguinWanderOnLandGoal(PenguinEntity penguin, double speed, int chance) {
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