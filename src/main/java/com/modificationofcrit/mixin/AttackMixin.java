package com.modificationofcrit.mixin;

import com.modificationofcrit.config.MyConfig;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

import static com.modificationofcrit.Main.*;

//移除下落暴击的机制
@Mixin(PlayerEntity.class)
public abstract class AttackMixin {
	public PlayerEntity originalInstance = (PlayerEntity) (Object) this;
	@Shadow
	public void attack(Entity target){}

	//判断是否暴击的方法
	private boolean isCrit(ItemStack itemstack, PlayerEntity player,float h){
		int rateLevel = EnchantmentHelper.getLevel(CRITRATE_ENCHANTMENT, itemstack);

		if (rateLevel > 0 && h > 0.9f) {
            return MyConfig.myRandom.nextInt(100) < rateLevel * MyConfig.RATE_IPL * 100;
		}
		return false;
	}


	@Inject(method = "attack", at = @At("HEAD"),cancellable = true)
	public void MyMixin(Entity target,CallbackInfo ci){
		if (!target.isAttackable()) {
			return;
		}
		if (target.handleAttack(originalInstance)) {
			return;
		}
		float f = (float)originalInstance.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
		float g = target instanceof LivingEntity ? EnchantmentHelper.getAttackDamage(originalInstance.getMainHandStack(), ((LivingEntity)target).getGroup()) : EnchantmentHelper.getAttackDamage(originalInstance.getMainHandStack(), EntityGroup.DEFAULT);
		float h = originalInstance.getAttackCooldownProgress(0.5f);
		g *= h;
		originalInstance.resetLastAttackedTicks();
		if ((f *= 0.2f + h * h * 0.8f) > 0.0f || g > 0.0f) {
			ItemStack itemStack;
			ItemStack heldItem = originalInstance.getMainHandStack();
			boolean bl = h > 0.9f;
			boolean bl2 = false;
			int i = 0;
			i += EnchantmentHelper.getKnockback(originalInstance);
			if (originalInstance.isSprinting() && bl) {
				originalInstance.getWorld().playSound(null, originalInstance.getX(), originalInstance.getY(), originalInstance.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, originalInstance.getSoundCategory(), 1.0f, 1.0f);
				++i;
				bl2 = true;
			}
			boolean bl3 = bl && originalInstance.fallDistance > 0.0f && !originalInstance.isOnGround() && !originalInstance.isClimbing() && !originalInstance.isTouchingWater() && !originalInstance.hasStatusEffect(StatusEffects.BLINDNESS) && !originalInstance.hasVehicle() && target instanceof LivingEntity;
			boolean crit = isCrit(heldItem,originalInstance,h);  //判断是否暴击
			boolean bl4 = bl3 = bl3 && !originalInstance.isSprinting();
			f += g;
			if (crit) {
				//暴击伤害计算
				f *= (float) (MyConfig.EFFECT_IE + MyConfig.EFFECT_IPL*EnchantmentHelper.getLevel(CRITEFFECT_ENCHANTMENT, heldItem));
			}
			boolean bl42 = false;
			double d = originalInstance.horizontalSpeed - originalInstance.prevHorizontalSpeed;
			if (bl && !bl3 && !bl2 && originalInstance.isOnGround() && d < (double) originalInstance.getMovementSpeed() && (itemStack = originalInstance.getStackInHand(Hand.MAIN_HAND)).getItem() instanceof SwordItem) {
				bl42 = true;
			}
			float j = 0.0f;
			boolean bl5 = false;
			int k = EnchantmentHelper.getFireAspect(originalInstance);
			if (target instanceof LivingEntity) {
				j = ((LivingEntity)target).getHealth();
				if (k > 0 && !target.isOnFire()) {
					bl5 = true;
					target.setOnFireFor(1);
				}
			}
			Vec3d vec3d = target.getVelocity();
			boolean bl6 = target.damage(originalInstance.getDamageSources().playerAttack(originalInstance), f);
			if (bl6) {
				if (i > 0) {
					if (target instanceof LivingEntity) {
						((LivingEntity)target).takeKnockback((float)i * 0.5f, MathHelper.sin(originalInstance.getYaw() * ((float)Math.PI / 180)), -MathHelper.cos(originalInstance.getYaw() * ((float)Math.PI / 180)));
					} else {
						target.addVelocity(-MathHelper.sin(originalInstance.getYaw() * ((float)Math.PI / 180)) * (float)i * 0.5f, 0.1, MathHelper.cos(originalInstance.getYaw() * ((float)Math.PI / 180)) * (float)i * 0.5f);
					}
					originalInstance.setVelocity(originalInstance.getVelocity().multiply(0.6, 1.0, 0.6));
					originalInstance.setSprinting(false);
				}
				if (bl42) {
					float l = 1.0f + EnchantmentHelper.getSweepingMultiplier(originalInstance) * f;
					List<LivingEntity> list = originalInstance.getWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0, 0.25, 1.0));
					for (LivingEntity livingEntity : list) {
						if (livingEntity == originalInstance || livingEntity == target || originalInstance.isTeammate(livingEntity) || livingEntity instanceof ArmorStandEntity && ((ArmorStandEntity)livingEntity).isMarker() || !(originalInstance.squaredDistanceTo(livingEntity) < 9.0)) continue;
						livingEntity.takeKnockback(0.4f, MathHelper.sin(originalInstance.getYaw() * ((float)Math.PI / 180)), -MathHelper.cos(originalInstance.getYaw() * ((float)Math.PI / 180)));
						livingEntity.damage(originalInstance.getDamageSources().playerAttack(originalInstance), l);
					}
					originalInstance.getWorld().playSound(null, originalInstance.getX(), originalInstance.getY(), originalInstance.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, originalInstance.getSoundCategory(), 1.0f, 1.0f);
					originalInstance.spawnSweepAttackParticles();
				}
				if (target instanceof ServerPlayerEntity && target.velocityModified) {
					((ServerPlayerEntity)target).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(target));
					target.velocityModified = false;
					target.setVelocity(vec3d);
				}
				if (crit) {
					//播放暴击音效和粒子效果
					originalInstance.getWorld().playSound(null, originalInstance.getX(), originalInstance.getY(), originalInstance.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, originalInstance.getSoundCategory(), 1.0f, 1.0f);
					originalInstance.addCritParticles(target);
				}
				if (bl3){
					//播放正常攻击音效
					originalInstance.getWorld().playSound(null, originalInstance.getX(), originalInstance.getY(), originalInstance.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, originalInstance.getSoundCategory(), 1.0f, 1.0f);

				}
				if (!bl3 && !bl42) {
					if (bl) {
						originalInstance.getWorld().playSound(null, originalInstance.getX(), originalInstance.getY(), originalInstance.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, originalInstance.getSoundCategory(), 1.0f, 1.0f);
					} else {
						originalInstance.getWorld().playSound(null, originalInstance.getX(), originalInstance.getY(), originalInstance.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, originalInstance.getSoundCategory(), 1.0f, 1.0f);
					}
				}
				if (g > 0.0f) {
					originalInstance.addEnchantedHitParticles(target);
				}
				originalInstance.onAttacking(target);
				if (target instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged((LivingEntity)target, originalInstance);
				}
				EnchantmentHelper.onTargetDamaged(originalInstance, target);
				ItemStack itemStack2 = originalInstance.getMainHandStack();
				Entity entity = target;
				if (target instanceof EnderDragonPart) {
					entity = ((EnderDragonPart)target).owner;
				}
				if (!originalInstance.getWorld().isClient && !itemStack2.isEmpty() && entity instanceof LivingEntity) {
					itemStack2.postHit((LivingEntity)entity, originalInstance);
					if (itemStack2.isEmpty()) {
						originalInstance.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
					}
				}
				if (target instanceof LivingEntity) {
					float m = j - ((LivingEntity)target).getHealth();
					originalInstance.increaseStat(Stats.DAMAGE_DEALT, Math.round(m * 10.0f));
					if (k > 0) {
						target.setOnFireFor(k * 4);
					}
					if (originalInstance.getWorld() instanceof ServerWorld && m > 2.0f) {
						int n = (int)((double)m * 0.5);
						((ServerWorld) originalInstance.getWorld()).spawnParticles(ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getBodyY(0.5), target.getZ(), n, 0.1, 0.0, 0.1, 0.2);
					}
				}
				originalInstance.addExhaustion(0.1f);
			} else {
				originalInstance.getWorld().playSound(null, originalInstance.getX(), originalInstance.getY(), originalInstance.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, originalInstance.getSoundCategory(), 1.0f, 1.0f);
				if (bl5) {
					target.extinguish();
				}
			}
		}
		ci.cancel();
	}
}