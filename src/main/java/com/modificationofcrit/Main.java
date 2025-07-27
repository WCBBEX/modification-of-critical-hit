package com.modificationofcrit;

import com.modificationofcrit.config.Config;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
	public static final String MOD_ID = "modification-of-critical-hit";

	@Override
	public void onInitialize() {
		new Config();
		ModEnchantments.registerModEnchantments();
	}
}