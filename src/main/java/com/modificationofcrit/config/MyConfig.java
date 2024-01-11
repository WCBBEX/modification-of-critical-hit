package com.modificationofcrit.config;
import com.modificationofcrit.config.SimpleConfig.ConfigRequest;
import net.minecraft.enchantment.Enchantment;

import java.util.Random;

public class MyConfig {
    public static int RATE_MAXLEVEL;
    public static int RATE_RARITY;
    public static double RATE_IPL;
    public static boolean RATE_ENABLE;

    public static int EFFFECT_MAXLEVEL;
    public static int EFFECT_RARITY;
    public static double EFFECT_IE;
    public static double EFFECT_IPL;
    public static boolean EFFECT_ENABLE;


    public static Random myRandom = new Random();

    public SimpleConfig CONFIG;
    public MyConfig() {
        ConfigRequest requester = SimpleConfig.of("ModificationOfCriticalHit");
        requester.provider(this::provider);
        CONFIG = requester.request();
        readConfig();
    }


    private String provider( String filename ) {
        return "crit_chance.rarity=1  #The larger the number, the rarer it is.There are a total of four levels.(1-4)\n" +
                "crit_chance.max_level=5\n" +
                "crit_chance.increase_per_level=0.2  #The initial critical hit chance will be 0.\n" +
                "crit_chance.enable=true\n"+
                "\n" +
                "crit_effect.rarity=3\n" +
                "crit_effect.max_level=3\n" +
                "crit_effect.initial_effect=1.5\n" +
                "crit_effect.increase_per_level=0.5\n" +
                "crit_effect.enable=true"
                ;
    }

    public void readConfig(){
        RATE_MAXLEVEL = CONFIG.getOrDefault("crit_chance.max_level",5);
        RATE_RARITY = CONFIG.getOrDefault("crit_chance.rarity",2);
        RATE_IPL = CONFIG.getOrDefault("crit_chance.increase_per_level",0.2);
        RATE_ENABLE = CONFIG.getOrDefault("crit_chance.enable",true);

        EFFFECT_MAXLEVEL = CONFIG.getOrDefault("crit_effect.max_level",3);
        EFFECT_RARITY = CONFIG.getOrDefault("crit_effect.rarity",3);
        EFFECT_IE = CONFIG.getOrDefault("crit_effect.initial_effect",1.5);
        EFFECT_IPL = CONFIG.getOrDefault("crit_effect.increase_per_level",0.5);
        EFFECT_ENABLE = CONFIG.getOrDefault("crit_effect.enable",true);

    }

    public static Enchantment.Rarity setRarity(int number){
        return switch (number) {
            case 1 -> Enchantment.Rarity.COMMON;
            case 3 -> Enchantment.Rarity.RARE;
            case 4 -> Enchantment.Rarity.VERY_RARE;
            default -> Enchantment.Rarity.UNCOMMON;
        };
    }
}
