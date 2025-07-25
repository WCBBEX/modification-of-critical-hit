package com.modificationofcrit.mixin;

import com.modificationofcrit.CritHelper;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class AttackMixin {

	@ModifyVariable(
			method = "attack",
			index = 8,
			at = @At(value = "STORE")
	)
	private boolean redefineBl3(boolean originalBl3) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		return CritHelper.isCustomCrit(player.getMainHandStack());
	}

	@ModifyConstant(
			method = "attack",
			constant = @Constant(floatValue = 1.5f)
	)
	private float modifyCritDamageMultiplier(float originalMultiplier) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		return CritHelper.getCritDamageMultiplier(player.getMainHandStack());
	}
}