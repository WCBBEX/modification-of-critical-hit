package com.modificationofcrit;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Main implements ModInitializer {
	public static final String MOD_ID = "modification-of-critical-hit";

	@Override
	public void onInitialize() {
		CritAttributes.register();
	}

}