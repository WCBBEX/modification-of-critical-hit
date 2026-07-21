package com.modificationofcrit.mixin;

import com.modificationofcrit.CritAttributes;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerAttributeMixin {

    @Inject(method = "createPlayerAttributes", at = @At("RETURN"), cancellable = true)
    private static void addCritAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        DefaultAttributeContainer.Builder builder = cir.getReturnValue();
        builder.add(CritAttributes.CRIT_CHANCE);
        builder.add(CritAttributes.CRIT_MODIFIER);
        cir.setReturnValue(builder);
    }
}
