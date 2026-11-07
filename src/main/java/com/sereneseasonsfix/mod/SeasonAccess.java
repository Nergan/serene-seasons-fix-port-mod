package com.sereneseasonsfix.mod;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import sereneseasons.init.ModConfig;

/** Kotlin cannot see the NightConfig types behind SeasonsConfig. */
public final class SeasonAccess {
    private SeasonAccess() {
    }

    public static boolean whitelisted(ResourceKey<Level> dimension) {
        return ModConfig.seasons.isDimensionWhitelisted(dimension);
    }
}
