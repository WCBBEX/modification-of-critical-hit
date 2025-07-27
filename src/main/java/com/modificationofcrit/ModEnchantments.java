package com.modificationofcrit;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;

public class ModEnchantments {

    public static Enchantment CRIT_CHANCE_ENCHANTMENT;
    public static Enchantment CRIT_EFFECT_ENCHANTMENT;

    public static void registerModEnchantments() {
        CRIT_CHANCE_ENCHANTMENT = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier(Main.MOD_ID, "crit_chance"),
                new Enchantment(
                        Enchantment.properties(
                                ItemTags.WEAPON_ENCHANTABLE, // supported_items
                                5,                           // weight
                                5,                           // max_level
                                Enchantment.leveledCost(1, 10), // min_cost
                                Enchantment.leveledCost(51, 10),// max_cost
                                2,                           // anvil_cost
                                // slots
                                EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND
                        )
                )
        );

        CRIT_EFFECT_ENCHANTMENT = Registry.register(
                Registries.ENCHANTMENT,
                new Identifier(Main.MOD_ID, "crit_effect"),
                new Enchantment(
                        Enchantment.properties(
                                ItemTags.WEAPON_ENCHANTABLE,
                                2,
                                3,
                                Enchantment.leveledCost(1, 10),
                                Enchantment.leveledCost(51, 10),
                                1,
                                EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND
                        )
                )
        );
    }
}