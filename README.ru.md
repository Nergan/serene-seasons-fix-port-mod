# Serene Seasons Fix

Порт [Serene Seasons Fix](https://github.com/123oro321/sereneseasonsfix) (Or_OS, MIT) на Fabric **1.21.1**. Оригинал — Forge до 1.20.2.

В Serene Seasons 1.21.1 сезон уже сдвигается на разницу дневного времени. Именно это делал тик оригинального фикса, и его страница говорит, что на более новом Serene Seasons оверрайд не нужен. Этот порт не отменяет `SeasonHandler.onLevelTick` и не меняет скорость сезона.

Что остаётся:

- `/season time info` показывает время сезона, время мира, разницу и то, входит ли измерение в белый список.
- `/season time sync` ставит часы сезона по дневному времени мира.
- `/season time set` и `/season time add` записывают часы сезона. Нужен уровень прав 2.
- `/season get` в измерении вне белого списка Serene Seasons пишет, что сезоны там выключены, если `block_blacklisted` включён.

`config/sereneseasonsfix-server.toml` содержит `block_blacklisted` (по умолчанию `true`). Настройки `enable_season_time_override` нет: перекрывать больше нечего.

Нужны [Serene Seasons](https://modrinth.com/mod/serene-seasons) Fabric 1.21.1 и его [GlitchCore](https://modrinth.com/mod/glitchcore), Fabric API и Fabric Language Kotlin. Мод ставится на сервер. В одиночной игре его поднимает встроенный сервер.

## Сборка

JDK 21 и Gradle 8.11 из этого каталога: `gradle build --stacktrace`.
