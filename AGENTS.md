# Serene Seasons Fix — инструкции для AI-агентов

Порт Fabric 1.21.1 мода Or_OS. Лицензия MIT, её не менять. Общие правила — `../../AGENTS.md`.

Тик сезона не переносить. В Serene Seasons 1.21.1 `SeasonHandler.onLevelTick` уже считает разницу `dayTime`. Отмена этого метода останавливает сезон. Команды `/season time` и отказ `/season get` вне белого списка — то, что в 1.21.1 ещё не было.

`GetSeasonMixin` — Java, потому что процессор аннотаций Loom сам пишет intermediary-имена. В Kotlin-миксин его не переписывать, пока рядом нет `patchKotlinRefmap`. Цель — метод мода `getSeason`, `remap = false`.

Зависимости Serene Seasons и GlitchCore берутся id версии Modrinth (`serene_seasons_maven`, `glitchcore_maven`): номер `10.1.0.9` общий у Fabric, Forge и NeoForge. Сборка — Gradle 8.11, JDK 21. McnCDN нет.
