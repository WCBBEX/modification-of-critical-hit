package com.modificationofcrit.config;

import com.modificationofcrit.config.SimpleConfig.ConfigRequest;

import java.util.Random;

public class Config {
    public static double RATE_IPL;
    public static boolean RATE_ENABLE;

    public static double EFFECT_IE;
    public static double EFFECT_IPL;
    public static boolean EFFECT_ENABLE;


    public static Random myRandom = new Random();

    public SimpleConfig CONFIG;

    public Config() {
        ConfigRequest requester = SimpleConfig.of("ModificationOfCriticalHit");
        requester.provider(this::provider);
        CONFIG = requester.request();
        readConfig();
    }


    private String provider( String filename ) {
        return """
                #The initial critical hit chance will be 0.
                crit_chance.increase_per_level=0.2
                crit_chance.enable=true

                crit_effect.initial_effect=1.5
                crit_effect.increase_per_level=0.5
                crit_effect.enable=true"""
                ;
    }

    public void readConfig(){
        RATE_IPL = CONFIG.getOrDefault("crit_chance.increase_per_level",0.2);
        RATE_ENABLE = CONFIG.getOrDefault("crit_chance.enable",true);

        EFFECT_IE = CONFIG.getOrDefault("crit_effect.initial_effect",1.5);
        EFFECT_IPL = CONFIG.getOrDefault("crit_effect.increase_per_level",0.5);
        EFFECT_ENABLE = CONFIG.getOrDefault("crit_effect.enable",true);

    }
}
