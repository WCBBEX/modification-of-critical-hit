package com.modificationofcrit.mixin;

import com.modificationofcrit.CritAttributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(PlayerEntity.class)
public abstract class AttackMixin {

	private static final Random RANDOM = new Random();

	@ModifyVariable(
			method = "attack",
			index = 8,
			at = @At(value = "STORE")
	)
	private boolean redefineBl3(boolean originalBl3) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		return isCustomCrit(player);
	}

	@ModifyConstant(
			method = "attack",
			constant = @Constant(floatValue = 1.5f)
	)
	private float modifyCritDamageMultiplier(float originalMultiplier) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		return getCritDamageMultiplier(player);
	}

	private static boolean isCustomCrit(PlayerEntity player) {
		var attributeInstance = player.getAttributeInstance(CritAttributes.CRIT_CHANCE);
		double critChance = attributeInstance != null
				? attributeInstance.getValue()
				: CritAttributes.CRIT_CHANCE.getDefaultValue();
		return RANDOM.nextDouble() < critChance;
	}

	private static float getCritDamageMultiplier(PlayerEntity player) {
		var attributeInstance = player.getAttributeInstance(CritAttributes.CRIT_MODIFIER);
		double modifier = attributeInstance != null
				? attributeInstance.getValue()
				: CritAttributes.CRIT_MODIFIER.getDefaultValue();
		return (float) modifier;
	}
}