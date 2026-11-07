package com.sereneseasonsfix.mod

import com.mojang.brigadier.arguments.IntegerArgumentType
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.TimeArgument
import net.minecraft.network.chat.Component
import net.minecraft.world.level.Level

object SeasonTimeCommands {
    fun register(dispatcher: com.mojang.brigadier.CommandDispatcher<net.minecraft.commands.CommandSourceStack>) {
        dispatcher.register(
            Commands.literal("season")
                .then(
                    Commands.literal("time")
                        .requires { it.hasPermission(2) }
                        .then(Commands.literal("info").executes { ctx -> info(ctx.source) })
                        .then(Commands.literal("sync").executes { ctx -> sync(ctx.source) })
                        .then(
                            Commands.literal("set").then(
                                Commands.argument("time", TimeArgument.time()).executes { ctx ->
                                    set(ctx.source, IntegerArgumentType.getInteger(ctx, "time").toLong())
                                },
                            ),
                        )
                        .then(
                            Commands.literal("add").then(
                                Commands.argument("time", TimeArgument.time()).executes { ctx ->
                                    val saved = SeasonRules.data(ctx.source.level) ?: return@executes refuse(ctx.source)
                                    set(ctx.source, saved.seasonCycleTicks.toLong() + IntegerArgumentType.getInteger(ctx, "time"))
                                },
                            ),
                        ),
                ),
        )
    }

    private fun info(source: net.minecraft.commands.CommandSourceStack): Int {
        val level = source.level
        val saved = SeasonRules.data(level) ?: return refuse(source)
        val seasonTime = saved.seasonCycleTicks
        val dayTime = level.dayTime
        val delta = seasonTime - SeasonCycle.wrap(dayTime, SeasonRules.duration())
        source.sendSuccess(
            {
                Component.translatable(
                    "commands.sereneseasonsfix.time.info",
                    seasonTime,
                    dayTime,
                    delta,
                    SeasonRules.allowed(level),
                )
            },
            true,
        )
        return delta.toInt()
    }

    private fun sync(source: net.minecraft.commands.CommandSourceStack): Int {
        val ticks = SeasonRules.setTicks(source.level, source.level.dayTime) ?: return refuse(source)
        source.sendSuccess({ Component.translatable("commands.sereneseasonsfix.time.sync_season.success") }, true)
        return ticks
    }

    private fun set(source: net.minecraft.commands.CommandSourceStack, ticks: Long): Int {
        val written = SeasonRules.setTicks(source.level, ticks) ?: return refuse(source)
        source.sendSuccess({ Component.translatable("commands.sereneseasonsfix.time.set_season.success", written) }, true)
        return written
    }

    private fun refuse(source: net.minecraft.commands.CommandSourceStack): Int {
        source.sendSuccess({ Component.translatable("commands.sereneseasonsfix.time.set_season.not_whitelisted") }, true)
        return 0
    }
}
