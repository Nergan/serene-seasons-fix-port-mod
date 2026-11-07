package com.sereneseasonsfix.mod

import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText

/** One server file. There is no tick override to switch: Serene Seasons 1.21.1 already follows the day clock. */
object FixConfig {
    var blockBlacklisted: Boolean = true
        private set

    fun load(configDir: Path) {
        val file = configDir.resolve("sereneseasonsfix-server.toml")
        if (!file.exists()) {
            file.parent?.createDirectories()
            file.writeText(
                """
                [general_settings]
                # Commands refuse a dimension that Serene Seasons does not whitelist.
                block_blacklisted = true
                """.trimIndent() + "\n",
            )
        }
        val match = Regex("""(?m)^\s*block_blacklisted\s*=\s*(true|false)\s*$""").find(file.readText())
        if (match != null) {
            blockBlacklisted = match.groupValues[1] == "true"
        }
    }
}
