package com.modificationofcrit;

import com.modificationofcrit.config.MyConfig;
import com.modificationofcrit.enchantment.CritEffectEnchantment;
import com.modificationofcrit.enchantment.CritRateEnchantment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
	public static final String MOD_ID = "modification-of-critical-hit";
	MyConfig ConfigStart = new MyConfig();
	public static CritRateEnchantment CRITRATE_ENCHANTMENT = new CritRateEnchantment();
	public static CritEffectEnchantment CRITEFFECT_ENCHANTMENT = new CritEffectEnchantment();


	@Override
	public void onInitialize() {
		if(MyConfig.RATE_ENABLE) {
			Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "crit_chance"), CRITRATE_ENCHANTMENT);
		}
		if(MyConfig.EFFECT_ENABLE) {
			Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "crit_effect"), CRITEFFECT_ENCHANTMENT);
		}
	}

}