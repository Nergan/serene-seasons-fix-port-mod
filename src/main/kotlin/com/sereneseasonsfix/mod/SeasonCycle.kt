package com.sereneseasonsfix.mod

/** The same wrap the original Forge fix used before writing season ticks. */
object SeasonCycle {
    fun wrap(ticks: Long, duration: Int): Int {
        val span = duration.toLong().coerceAtLeast(1L)
        val value = ticks % span
        return (if (value < 0) value + span else value).toInt()
    }
}
