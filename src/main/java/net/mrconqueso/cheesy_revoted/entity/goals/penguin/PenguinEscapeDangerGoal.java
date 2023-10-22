package net.mrconqueso.cheesy_revoted.entity.goals.penguin;

import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.util.math.BlockPos;
import net.mrconqueso.cheesy_revoted.entity.custom.PenguinEntity;

public class PenguinEscapeDangerGoal extends EscapeDangerGoal {
    public PenguinEscapeDangerGoal(PenguinEntity penguin, double speed) {
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
