package com.modificationofcrit;

import com.modificationofcrit.config.Config;
import net.minecraft.item.ItemStack;

public class CritHelper {

    public static boolean isCustomCrit(ItemStack weapon) {
        int rateLevel = weapon.getEnchantments().getLevel(ModEnchantments.CRIT_CHANCE_ENCHANTMENT);
        if (rateLevel <= 0) {
            return false;
        }
        double critChance = rateLevel * Config.RATE_IPL;
        return Config.myRandom.nextDouble() < critChance;
    }

    public static float getCritDamageMultiplier(ItemStack weapon) {
        int effectLevel = weapon.getEnchantments().getLevel(ModEnchantments.CRIT_EFFECT_ENCHANTMENT);
        return (float) (Config.EFFECT_IE + Config.EFFECT_IPL * effectLevel);
    }
}