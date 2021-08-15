package xyz.lamergameryt.lamerutils.generic

import org.junit.Test
import kotlin.test.assertEquals

internal class TimeUtilsTest {
    @Test
    fun testTimeParser() {
        // 5 days, 15 hours, 10 minutes and 2 seconds = 313802 seconds.
        val timeString = "3 days 15 hours and 10m2s"
        val parser = TimeUtils.TimeParser(timeString)

        assertEquals(313802, parser.seconds)
    }

    @Test
    fun testSecondsConverter() {
        // 29 minutes and 39 seconds
        val seconds = 1539L
        val converted = TimeUtils.convertSecondsToText(seconds)

        assertEquals("25m 39s", converted)
    }
}
