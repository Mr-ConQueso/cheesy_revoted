package net.mrconqueso.cheesy_revoted.blocks.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import net.mrconqueso.cheesy_revoted.blocks.ModBlocks;
import net.mrconqueso.cheesy_revoted.entity.ModEntities;
import net.mrconqueso.cheesy_revoted.entity.custom.PenguinEntity;
import net.mrconqueso.cheesy_revoted.util.ModTags;
import org.jetbrains.annotations.Nullable;

public class PenguinEggBlock extends Block {
    public PenguinEggBlock(Settings settings) {
        super(settings);
    }
    private static final VoxelShape EGG_SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 9.0, 12.0);
    public static final IntProperty HATCH = Properties.HATCH;
    public static final IntProperty EGGS = IntProperty.of("eggs", 1, 2);

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.bypassesSteppingEffects()) {
            this.tryBreakEgg(world, state, pos, entity, 100);
        }
        super.onSteppedOn(world, pos, state, entity);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!(entity instanceof ZombieEntity)) {
            this.tryBreakEgg(world, state, pos, entity, 3);
        }
        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    private void tryBreakEgg(World world, BlockState state, BlockPos pos, Entity entity, int inverseChance) {
        if (!this.breaksEgg(world, entity)) {
            return;
        }
        if (!world.isClient && world.random.nextInt(inverseChance) == 0 && state.isOf(ModBlocks.PENGUIN_EGG)) {
            this.breakEgg(world, pos, state);
        }
    }

    private void breakEgg(World world, BlockPos pos, BlockState state) {
        world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7f, 0.9f + world.random.nextFloat() * 0.2f);
        int i = state.get(EGGS);
        if (i <= 1) {
            world.breakBlock(pos, false);
        } else {
            world.setBlockState(pos, (BlockState)state.with(EGGS, i - 1), Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state));
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.shouldHatchProgress(world) && isGravelBelow(world, pos)) {
            int i = state.get(HATCH);
            if (i < 2) {
                world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
                world.setBlockState(pos, (BlockState)state.with(HATCH, i + 1), Block.NOTIFY_LISTENERS);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(state));
            } else {
                world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
                world.removeBlock(pos, false);
                world.emitGameEvent(GameEvent.BLOCK_DESTROY, pos, GameEvent.Emitter.of(state));
                for (int j = 0; j < state.get(EGGS); ++j) {
                    world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
                    PenguinEntity penguinEntity = ModEntities.PENGUIN.create(world);
                    if (penguinEntity == null) continue;
                    penguinEntity.setBreedingAge(-24000);
                    penguinEntity.refreshPositionAndAngles((double)pos.getX() + 0.3 + (double)j * 0.2, pos.getY(), (double)pos.getZ() + 0.3, 0.0f, 0.0f);
                    world.spawnEntity(penguinEntity);
                }
            }
        }
    }

    public static boolean isGravelBelow(BlockView world, BlockPos pos) {
        return PenguinEggBlock.isGravel(world, pos.down());
    }

    public static boolean isGravel(BlockView world, BlockPos pos) {
        return world.getBlockState(pos).isIn(ModTags.Blocks.PENGUIN_LAY_EGG_ON);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if (PenguinEggBlock.isGravelBelow(world, pos) && !world.isClient) {
            world.syncWorldEvent(WorldEvents.PLANT_FERTILIZED, pos, 0);
        }
    }

    private boolean shouldHatchProgress(World world) {
        float f = world.getSkyAngle(1.0f);
        if ((double)f < 0.69 && (double)f > 0.65) {
            return true;
        }
        return world.random.nextInt(500) == 0;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        this.breakEgg(world, pos, state);
    }

    @Deprecated
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (!context.shouldCancelInteraction() && context.getStack().isOf(this.asItem()) && state.get(EGGS) < 4) {
            return true;
        }
        return super.canReplace(state, context);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return (BlockState)blockState.with(EGGS, Math.min(2, blockState.get(EGGS) + 1));
        }
        return super.getPlacementState(ctx);
    }

    @Deprecated
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return EGG_SHAPE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HATCH, EGGS);
    }

    private boolean breaksEgg(World world, Entity entity) {
        if (entity instanceof PenguinEntity || entity instanceof BatEntity) {
            return false;
        }
        if (entity instanceof LivingEntity) {
            return entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
        }
        return false;
    }
}
