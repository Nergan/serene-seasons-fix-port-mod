package com.sereneseasonsfix.mod

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.loader.api.FabricLoader
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class SereneSeasonsFix : ModInitializer {
    override fun onInitialize() {
        FixConfig.load(FabricLoader.getInstance().configDir)
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            SeasonTimeCommands.register(dispatcher)
        }
        LOGGER.info("Serene Seasons Fix is not replacing the season tick. Serene Seasons 1.21.1 already follows the day clock.")
    }

    companion object {
        const val MOD_ID: String = "sereneseasonsfix"
        val LOGGER: Logger = LoggerFactory.getLogger(MOD_ID)
    }
}
