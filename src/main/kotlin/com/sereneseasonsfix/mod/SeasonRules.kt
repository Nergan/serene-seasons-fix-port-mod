package com.sereneseasonsfix.mod

import net.minecraft.world.level.Level
import sereneseasons.season.SeasonHandler
import sereneseasons.season.SeasonSavedData
import sereneseasons.season.SeasonTime

object SeasonRules {
    fun allowed(level: Level): Boolean {
        if (!FixConfig.blockBlacklisted) return true
        return SeasonAccess.whitelisted(level.dimension())
    }

    fun data(level: Level): SeasonSavedData? = SeasonHandler.getSeasonSavedData(level)

    fun duration(): Int = SeasonTime.ZERO.cycleDuration

    fun setTicks(level: Level, ticks: Long): Int? {
        if (!allowed(level)) return null
        val saved = data(level) ?: return null
        saved.seasonCycleTicks = SeasonCycle.wrap(ticks, duration())
        saved.setDirty()
        SeasonHandler.sendSeasonUpdate(level)
        return saved.seasonCycleTicks
    }
}
