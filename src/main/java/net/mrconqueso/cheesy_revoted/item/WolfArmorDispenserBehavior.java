package net.mrconqueso.cheesy_revoted.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;

import java.util.Iterator;
import java.util.List;

public class WolfArmorDispenserBehavior extends FallibleItemDispenserBehavior {

    @Override
    public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
        BlockPos blockPos = pointer.pos().offset(pointer.state().get(DispenserBlock.FACING));

        List<WolfEntity> wolves = pointer
                .world()
                .getEntitiesByClass(WolfEntity.class, new Box(blockPos), wolf -> wolf.isAlive() && CheesyRevoted.getData(wolf).getWolfArmor().isEmpty());

        Iterator<WolfEntity> iterator = wolves.iterator();
        WolfEntity wolf;
        do {
            if (!iterator.hasNext()) {
                return super.dispenseSilently(pointer, stack);
            }

            wolf = iterator.next();
        } while(!(stack.getItem() instanceof WolfArmorItem) || !wolf.isTamed());

        CheesyRevoted.getData(wolf).setWolfArmor(stack.split(1));
        this.setSuccess(true);
        return stack;
    }
}
