package com.modificationofcrit;

import com.modificationofcrit.config.MyConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class CritHelper {

    public static boolean isCustomCrit(ItemStack weapon) {
        int rateLevel = EnchantmentHelper.getLevel(Main.CRITRATE_ENCHANTMENT, weapon);
        if (rateLevel <= 0) {
            return false;
        }
        double critChance = rateLevel * MyConfig.RATE_IPL;
        return MyConfig.myRandom.nextDouble() < critChance;
    }

    public static float getCritDamageMultiplier(ItemStack weapon) {
        int effectLevel = EnchantmentHelper.getLevel(Main.CRITEFFECT_ENCHANTMENT, weapon);
        return (float) (MyConfig.EFFECT_IE + MyConfig.EFFECT_IPL * effectLevel);
    }
}