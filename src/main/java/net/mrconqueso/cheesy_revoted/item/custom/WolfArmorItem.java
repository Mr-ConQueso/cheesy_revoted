package net.mrconqueso.cheesy_revoted.item.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.mrconqueso.cheesy_revoted.CheesyRevoted;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WolfArmorItem extends Item {
    private final int bonus;
    private final Identifier entityTexture;

    public WolfArmorItem(Settings settings) {
        super(settings);
        this.bonus = 5;
        this.entityTexture = new Identifier(CheesyRevoted.MOD_ID, "textures/entity/wolf/armor/wolf_armor.png");
    }

    // --------- / WOLF ENTITY ARMOR TEXTURE / --------- //
    public Identifier getEntityTexture() {
        return this.entityTexture;
    }

    // --------- / GET ENCHANTMENTS / --------- //
    public int getBonus(ItemStack stack) {
        int protectionLevel = EnchantmentHelper.getLevel(Enchantments.PROTECTION, stack) * 2;
        return this.bonus + protectionLevel;
    }

    // --------- / ADDED ARMOR TOOLTIP / --------- //
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.of(""));
        tooltip.add(Text.translatable("item.cheesy_revoted.wolf_armor.tooltip.when_equipped").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.cheesy_revoted.wolf_armor.tooltip.bonus", getBonus(stack)).formatted(Formatting.BLUE));

        if(stack.hasEnchantments()) {
            tooltip.add(Text.of(""));
        }
    }
}
