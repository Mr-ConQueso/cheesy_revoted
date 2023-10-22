package net.mrconqueso.cheesy_revoted.entity.goals.penguin;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.MathHelper;
import net.mrconqueso.cheesy_revoted.entity.custom.PenguinEntity;

public class PenguinMoveControl extends MoveControl {
    private final PenguinEntity penguin;

    public PenguinMoveControl(PenguinEntity penguin) {
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
            this.penguin.setMovementSpeed(Math.max(this.penguin.getMovementSpeed() / 1.15f, 0.06f));
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