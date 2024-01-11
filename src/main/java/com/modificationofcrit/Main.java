package com.modificationofcrit;

import com.modificationofcrit.config.MyConfig;
import com.modificationofcrit.enchantment.CritEffectEnchantment;
import com.modificationofcrit.enchantment.CritRateEnchantment;
import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Main implements ModInitializer {
	public static final String MOD_ID = "modification-of-critical-hit";
	MyConfig ConfigStart = new MyConfig();
	public static CritRateEnchantment CRITRATE_ENCHANTMENT = new CritRateEnchantment(); //暴击率附魔
	public static CritEffectEnchantment CRITEFFECT_ENCHANTMENT = new CritEffectEnchantment();//暴击效果附魔



	@Override
	public void onInitialize() {
		//根据config文件中的设置来注册附魔
		if(MyConfig.RATE_ENABLE) {
			Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, "critchance"), CRITRATE_ENCHANTMENT);
		}
		if(MyConfig.EFFECT_ENABLE) {
			Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, "criteffect"), CRITEFFECT_ENCHANTMENT);
		}
	}

}