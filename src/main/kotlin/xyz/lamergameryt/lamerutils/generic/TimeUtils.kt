@file:Suppress("unused")

package xyz.lamergameryt.lamerutils.generic

import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Predicate

/**
 * This class contains various static utilities for time handling and parsing.
 *
 * @sample xyz.lamergameryt.lamerutils.samples.LamerUtilsKotlinExample.timeUtilsExample
 */
class TimeUtils {
    /**
     * This object defines all the static variables and methods present in [TimeUtils].
     */
    companion object {
        /**
         * Converts [seconds] entered into hours, minutes and seconds.
         *
         * Example: 500 seconds get converted to `8m 20s`.
         *
         * @return The seconds converted as a [String].
         */
        @JvmStatic
        fun convertSecondsToText(seconds: Long): String {
            return Duration.ofSeconds(seconds).toString().substring(2)
                .replace("(\\d[HMS])(?!$)".toRegex(), "$1 ").lowercase(Locale.getDefault())
        }
    }

    /**
     * This class is used to convert time strings to their respective value.
     *
     * Example: 3m 5s will be converted to 185 seconds (you can retrieve as other time formats as well)
     */
    @Suppress("MemberVisibilityCanBePrivate")
    class TimeParser {
        var seconds: Long = 0
            private set

        /**
         * Creates an instance of the class with the [time] string.
         */
        constructor(time: String) {
            reparse(time)
        }

        /**
         * Creates an instance of the class with the [TimeParser.seconds] equal to [seconds].
         */
        constructor(seconds: Long) {
            this.seconds = seconds
        }

        private fun reparse(time: String) {
            seconds = 0
            val scanner = TimeScanner(
                time
                    .replace(" ", "")
                    .replace("and", "")
                    .replace(",", "")
                    .lowercase(Locale.getDefault())
            )
            var next: Long
            while (scanner.hasNext()) {
                next = scanner.nextLong()
                seconds += when (scanner.nextString()) {
                    "s", "sec", "secs", "second", "seconds" -> next
                    "m", "min", "mins", "minute", "minutes" -> TimeUnit.MINUTES.toSeconds(next)
                    "h", "hr", "hrs", "hour", "hours" -> TimeUnit.HOURS.toSeconds(next)
                    "d", "dy", "dys", "day", "days" -> TimeUnit.DAYS.toSeconds(next)
                    "w", "week", "weeks" -> TimeUnit.DAYS.toSeconds(next * daysInWeek)
                    "mo", "mon", "mnth", "month", "months" -> TimeUnit.DAYS.toSeconds(next * daysInMonth)
                    "y", "yr", "yrs", "year", "years" -> TimeUnit.DAYS.toSeconds(next * daysInYear)
                    else -> throw IllegalArgumentException()
                }
            }
        }

        /**
         * Returns the date as string with the [TimeParser.seconds] seconds added to it.
         *
         * Example: 2h 15m will return `Sat, 14 Aug 2021 23:36:37` if the current date is `Sat, 14 Aug 2021 21:21:37`.
         */
        val timeAsDateString: String
            get() {
                val formatted = ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).plusSeconds(seconds).format(
                    DateTimeFormatter.RFC_1123_DATE_TIME
                )
                return formatted.substring(0, formatted.length - 6)
            }

        /**
         * Returns the number of nanoseconds [TimeParser.seconds] seconds is equal to.
         */
        val nanoseconds: Long
            get() = TimeUnit.SECONDS.toNanos(seconds)

        /**
         * Returns the number of microseconds [TimeParser.seconds] seconds is equal to.
         */
        val microseconds: Long
            get() = TimeUnit.SECONDS.toMicros(seconds)

        /**
         * Returns the number of milliseconds [TimeParser.seconds] seconds is equal to.
         */
        val milliseconds: Long
            get() = TimeUnit.SECONDS.toMillis(seconds)

        /**
         * Returns the number of minutes [TimeParser.seconds] seconds is equal to.
         */
        val minutes: Double
            get() = seconds / 60.0

        /**
         * Returns the number of hours [TimeParser.seconds] seconds is equal to.
         */
        val hours: Double
            get() = seconds / 3600.0

        /**
         * Returns the number of days [TimeParser.seconds] seconds is equal to.
         */
        val days: Double
            get() = seconds / 86400.0

        /**
         * Returns the number of weeks [TimeParser.seconds] seconds is equal to.
         */
        val weeks: Double
            get() = days / daysInWeek

        /**
         * Returns the number of months [TimeParser.seconds] seconds is equal to.
         */
        val months: Double
            get() = days / daysInMonth

        /**
         * Returns the number of years [TimeParser.seconds] seconds is equal to.
         */
        val years: Double
            get() = days / daysInYear


        private val daysInWeek: Long = 7
        private val daysInMonth: Long = 30
        private val daysInYear: Long = 365
    }

    private class TimeScanner(time: String) {
        private val time: CharArray
        private var index = 0

        init {
            this.time = time.toCharArray()
        }

        operator fun hasNext(): Boolean {
            return index < time.size - 1
        }

        fun nextLong(): Long {
            return String(next { ch: Char? -> Character.isDigit(ch!!) }).toLong()
        }

        fun nextString(): String {
            return String(next { codePoint: Char ->
                Character.isAlphabetic(
                    codePoint.code
                )
            })
        }

        private fun next(whichSatisfies: Predicate<Char>): CharArray {
            val startIndex = index
            @Suppress("ControlFlowWithEmptyBody")
            while (++index < time.size && whichSatisfies.test(time[index]));
            return time.copyOfRange(startIndex, index)
        }
    }
}
