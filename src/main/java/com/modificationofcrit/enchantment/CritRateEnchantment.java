package com.modificationofcrit.enchantment;

import com.modificationofcrit.config.MyConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;

import java.util.Random;



public class CritRateEnchantment extends Enchantment {

    public CritRateEnchantment() {
        super(MyConfig.setRarity(MyConfig.RATE_RARITY), EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }

    @Override
    public int getMinPower(int level) {
        return 1 + 10 * (level - 1);
    }

    @Override
    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        boolean isAccept = EnchantmentTarget.WEAPON.isAcceptableItem(stack.getItem()) || EnchantmentTarget.TRIDENT.isAcceptableItem(stack.getItem()) || (stack.getItem() instanceof AxeItem);
        return isAccept;
    }

    @Override
    public int getMaxLevel() {
        return MyConfig.RATE_MAXLEVEL;
    }
}