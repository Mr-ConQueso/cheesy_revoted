package net.mrconqueso.item.custom;


import net.minecraft.client.item.TooltipContext;
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



    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.cheesy_revoted.crab_claw.tooltip").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.cheesy_revoted.crab_claw.tooltip_2").formatted(Formatting.DARK_GREEN));
    }
}
