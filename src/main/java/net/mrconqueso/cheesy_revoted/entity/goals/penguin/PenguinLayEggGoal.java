package net.mrconqueso.cheesy_revoted.entity.goals.penguin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.mrconqueso.cheesy_revoted.blocks.ModBlocks;
import net.mrconqueso.cheesy_revoted.blocks.custom.PenguinEggBlock;
import net.mrconqueso.cheesy_revoted.entity.custom.PenguinEntity;

public class PenguinLayEggGoal extends MoveToTargetPosGoal {
    private final PenguinEntity penguin;
    private final Random random = Random.create();
    public PenguinLayEggGoal(PenguinEntity penguin, double speed) {
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

            int eggAmount = 1;
            World world = this.penguin.getWorld();
            world.playSound(null, blockPos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3f, 0.9f + world.random.nextFloat() * 0.2f);
            BlockPos blockPos2 = this.targetPos.up();
            if (random.nextFloat() > 0.85) {
                eggAmount = 2;
            }
            BlockState blockState = (BlockState) ModBlocks.PENGUIN_EGG.getDefaultState().with(PenguinEggBlock.EGGS, eggAmount);
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
        return PenguinEggBlock.isGravel(world, pos);
    }
}