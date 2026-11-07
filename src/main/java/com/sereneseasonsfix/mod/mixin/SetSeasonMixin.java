package com.sereneseasonsfix.mod.mixin;

import com.sereneseasonsfix.mod.SeasonRules;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sereneseasons.api.season.Season;
import sereneseasons.command.CommandSetSeason;

/** /season set does nothing in a dimension Serene Seasons does not use. */
@Mixin(CommandSetSeason.class)
public abstract class SetSeasonMixin {
    @Inject(method = "setSeason", at = @At("HEAD"), cancellable = true, remap = false)
    private static void sereneseasonsfix$refuse(
        CommandSourceStack source,
        Level level,
        Season.SubSeason season,
        CallbackInfoReturnable<Integer> callback
    ) {
        if (SeasonRules.INSTANCE.allowed(level)) {
            return;
        }
        source.sendSuccess(() -> Component.translatable("commands.sereneseasonsfix.get_season.not_whitelisted"), true);
        callback.setReturnValue(1);
    }
}
