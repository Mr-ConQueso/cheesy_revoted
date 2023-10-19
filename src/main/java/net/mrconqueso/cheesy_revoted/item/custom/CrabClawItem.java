package net.mrconqueso.cheesy_revoted.item.custom;


import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrabClawItem extends ToolItem {
    public CrabClawItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    // --------- / EXTEND REACH WHEN HELD / --------- //
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (slot == 99 || slot == 98) {

        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }

    // --------- / ADDED REACH TOOLTIP / --------- //
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.cheesy_revoted.crab_claw.tooltip").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.cheesy_revoted.crab_claw.tooltip_2").formatted(Formatting.DARK_GREEN));
    }
}
