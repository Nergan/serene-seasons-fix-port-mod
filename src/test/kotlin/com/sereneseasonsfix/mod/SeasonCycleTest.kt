package com.sereneseasonsfix.mod

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SeasonCycleTest {
    @Test
    fun wrapsForwardAndBackward() {
        assertEquals(5, SeasonCycle.wrap(5, 100))
        assertEquals(0, SeasonCycle.wrap(100, 100))
        assertEquals(99, SeasonCycle.wrap(-1, 100))
        assertEquals(40, SeasonCycle.wrap(-60, 100))
    }
}
