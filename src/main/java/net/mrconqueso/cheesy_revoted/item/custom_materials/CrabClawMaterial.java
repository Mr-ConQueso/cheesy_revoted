package net.mrconqueso.cheesy_revoted.item.custom_materials;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.mrconqueso.cheesy_revoted.item.ModItems;

public class CrabClawMaterial implements ToolMaterial {
    public static final CrabClawMaterial CRAB_CLAW_MATERIAL = new CrabClawMaterial();
    @Override
    public int getDurability() {
        return 250;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 2.0f;
    }

    @Override
    public float getAttackDamage() {
        return 3.0f;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 3;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(ModItems.CRAB_CLAW);
    }
}
