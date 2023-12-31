package net.mrconqueso.cheesy_revoted.item;


import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
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
    private int extendedReach = 3;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        //this.getAttributeModifiers(stack, EquipmentSlot.OFFHAND).put(ReachEntityAttributes.REACH, 3.0f);
        //this.getAttributeModifiers(stack, EquipmentSlot.OFFHAND).put(EntityAttributes.GENERIC_MAX_HEALTH, EntityAttributeModifier.Operation.ADDITION.);


        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return super.getAttributeModifiers(slot);


    }

    // --------- / ADDED REACH TOOLTIP / --------- //
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal(""));
        tooltip.add(Text.translatable("item.cheesy_revoted.crab_claw.tooltip.when_held").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.cheesy_revoted.crab_claw.tooltip.bonus", extendedReach).formatted(Formatting.DARK_GREEN));

        if(stack.hasEnchantments()) {
            tooltip.add(Text.of(""));
        }
    }
}
