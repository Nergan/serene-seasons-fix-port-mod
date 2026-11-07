# Serene Seasons Fix

A Fabric **1.21.1** port of [Serene Seasons Fix](https://github.com/123oro321/sereneseasonsfix) by Or_OS. License: MIT. The original is Forge, through 1.20.2.

Serene Seasons 1.21.1 already advances the season by the change in day time. That is the tick this fix used to install, and its own page says a newer Serene Seasons does not need that override. This port does not cancel or replace `SeasonHandler.onLevelTick`. Installing it does not change how fast the season moves.

What it still adds:

- `/season time info` shows season time, world time, the difference, and whether this dimension is whitelisted.
- `/season time sync` sets the season clock from the world day time.
- `/season time set` and `/season time add` write the season clock. They need permission level 2.
- `/season get` in a dimension Serene Seasons does not whitelist says that seasons are off there, when `block_blacklisted` is true.

`config/sereneseasonsfix-server.toml` has `block_blacklisted` (default `true`). There is no `enable_season_time_override`: nothing is left to override.

Required: [Serene Seasons](https://modrinth.com/mod/serene-seasons) Fabric 1.21.1, which brings [GlitchCore](https://modrinth.com/mod/glitchcore), plus Fabric API and Fabric Language Kotlin. The GitHub release page for this port includes those jars. The mod belongs on the server. A single-player world loads it with the integrated server.

## Building

JDK 21 and Gradle 8.11, from this directory: `gradle build --stacktrace`.
