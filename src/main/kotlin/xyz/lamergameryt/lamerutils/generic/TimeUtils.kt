@file:Suppress("unused")

package xyz.lamergameryt.lamerutils.generic

import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Predicate

class TimeUtils {
    companion object {
        /**
         * Converts [seconds] entered into hours, minutes and seconds.
         *
         * Example: 500 seconds get converted to `8m 20s`.
         *
         * @return the seconds converted as a [String].
         */
        @JvmStatic
        fun getSecondsAsConverted(seconds: Long): String {
            return Duration.ofSeconds(seconds).toString().substring(2)
                .replace("(\\d[HMS])(?!$)".toRegex(), "$1 ").lowercase(Locale.getDefault())
        }
    }

    /**
     * This class is used to convert time strings to their respective value.
     *
     * Example: 3m5s will be converted to 185 seconds (you can retrieve as more time formats as well)
     */
    @Suppress("MemberVisibilityCanBePrivate")
    class TimeParser {
        var seconds: Long = 0
            private set

        constructor(time: String) {
            reparse(time)
        }

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
                    "w", "week", "weeks" -> TimeUnit.DAYS.toSeconds(next * DAYS_IN_WEEK)
                    "mo", "mon", "mnth", "month", "months" -> TimeUnit.DAYS.toSeconds(next * DAYS_IN_MONTH)
                    "y", "yr", "yrs", "year", "years" -> TimeUnit.DAYS.toSeconds(next * DAYS_IN_YEAR)
                    else -> throw IllegalArgumentException()
                }
            }
        }

        val timeAsString: String
            get() {
                val formatted = ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).plusSeconds(seconds).format(
                    DateTimeFormatter.RFC_1123_DATE_TIME
                )
                return formatted.substring(0, formatted.length - 6)
            }
        val nanoseconds: Long
            get() = TimeUnit.SECONDS.toNanos(seconds)
        val microseconds: Long
            get() = TimeUnit.SECONDS.toMicros(seconds)
        val milliseconds: Long
            get() = TimeUnit.SECONDS.toMillis(seconds)
        val minutes: Double
            get() = seconds / 60.0
        val hours: Double
            get() = seconds / 3600.0
        val days: Double
            get() = seconds / 86400.0
        val weeks: Double
            get() = days / DAYS_IN_WEEK
        val months: Double
            get() = days / DAYS_IN_MONTH
        val years: Double
            get() = days / DAYS_IN_YEAR

        companion object {
            private const val DAYS_IN_WEEK: Long = 7
            private const val DAYS_IN_MONTH: Long = 30
            private const val DAYS_IN_YEAR: Long = 365
        }
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
