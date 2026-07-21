package com.modificationofcrit;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.RangedAttribute;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class CritAttributes {

    public static final EntityAttribute CRIT_CHANCE = new RangedAttribute(
            "attribute.name.modification-of-critical-hit.crit_chance",
            0.05,
            0.0,
            1.0
    ).setTracked(true);

    public static final EntityAttribute CRIT_MODIFIER = new RangedAttribute(
            "attribute.name.modification-of-critical-hit.crit_modifier",
            1.5,
            0.0,
            100.0
    ).setTracked(true);

    private CritAttributes() {
    }

    public static void register() {
        Registry.register(Registry.ATTRIBUTE, new Identifier(Main.MOD_ID, "crit_chance"), CRIT_CHANCE);
        Registry.register(Registry.ATTRIBUTE, new Identifier(Main.MOD_ID, "crit_modifier"), CRIT_MODIFIER);
    }
}
