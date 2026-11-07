package com.sereneseasonsfix.mod.mixin;

import com.sereneseasonsfix.mod.SeasonRules;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sereneseasons.command.CommandGetSeason;

/** A dimension Serene Seasons ignores gets a plain message instead of a season. */
@Mixin(CommandGetSeason.class)
public abstract class GetSeasonMixin {
    @Inject(method = "getSeason", at = @At("HEAD"), cancellable = true, remap = false)
    private static void sereneseasonsfix$refuse(CommandSourceStack source, Level level, CallbackInfoReturnable<Integer> callback) {
        if (SeasonRules.INSTANCE.allowed(level)) {
            return;
        }
        source.sendSuccess(() -> Component.translatable("commands.sereneseasonsfix.get_season.not_whitelisted"), true);
        callback.setReturnValue(1);
    }
}
