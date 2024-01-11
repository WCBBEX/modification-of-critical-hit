package com.modificationofcrit.enchantment;

import com.modificationofcrit.config.MyConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;

public class CritEffectEnchantment extends Enchantment {

    public CritEffectEnchantment() {
        super(MyConfig.setRarity(MyConfig.EFFECT_RARITY), EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }
    @Override
    public int getMinPower(int level) {
        return 1 + 10 * (level - 1);
    }
    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }
    @Override
    public int getMaxLevel() {
        return MyConfig.EFFFECT_MAXLEVEL;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        boolean isAccept = EnchantmentTarget.WEAPON.isAcceptableItem(stack.getItem())|| EnchantmentTarget.TRIDENT.isAcceptableItem(stack.getItem()) || (stack.getItem() instanceof AxeItem);
        return isAccept;
    }

}