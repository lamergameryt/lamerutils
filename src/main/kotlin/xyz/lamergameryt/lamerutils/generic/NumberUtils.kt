package xyz.lamergameryt.lamerutils.generic

import java.text.DecimalFormat
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

/**
 * This class contains various static utilities for numbers.
 *
 * @sample xyz.lamergameryt.lamerutils.samples.LamerUtilsKotlinExample.numberUtilsExample
 */
@Suppress("unused")
class NumberUtils {
    /**
     * This object defines all the static variables and methods present in [NumberUtils].
     */
    companion object {
        /**
         * Function to abbreviate [numValue] using default suffixes.
         *
         * Example: 1,000,000 gets abbreviated to 1M
         *
         * @return The abbreviated number as a [String]
         */
        @JvmStatic
        fun abbreviateNumber(numValue: Double): String? {
            return abbreviateNumber(numValue, arrayOf(" ", "k", "M", "B", "T", "q", "Q", "s", "S"))
        }

        /**
         * Function to abbreviate a [numValue] using the [suffixes] ordered by chronological order.
         *
         * Example: 1,000,000 gets abbreviated to 1M
         *
         * @return The abbreviated number as a [String]
         */
        @JvmStatic
        private fun abbreviateNumber(numValue: Double, suffixes: Array<String>): String? {
            val value = floor(log10(numValue)).toInt()
            val base = value / 3
            return if (value >= 3 && base < suffixes.size) {
                DecimalFormat("~#0.0").format(numValue / 10.0.pow((base * 3).toDouble())) + suffixes[base]
            } else {
                DecimalFormat("#,##0").format(numValue)
            }
        }
    }
}
