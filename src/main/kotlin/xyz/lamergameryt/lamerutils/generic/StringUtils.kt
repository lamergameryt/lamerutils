package xyz.lamergameryt.lamerutils.generic

import java.security.SecureRandom
import java.util.*


/**
 * This class contains various static utilities for strings.
 */
@Suppress("unused", "SpellCheckingInspection")
class StringUtils {
    companion object {
        private const val upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        private const val lower = "abcdefghijklmnopqrstuvwxyz"
        private const val digits = "0123456789"
        private const val alphanum = upper + lower + digits

        private val random: Random = SecureRandom()
        private val symbols: CharArray = alphanum.toCharArray()

        /**
         * Returns an alphanumeric string of [length] characters.
         *
         * @return a random alphanumeric string.
         */
        @JvmStatic
        fun getRandomString(length: Int): String {
            val buf = CharArray(length)
            for (idx in buf.indices) buf[idx] = symbols[random.nextInt(symbols.size)]
            return String(buf)
        }

        /**
         * Capitalizes the first letter of the [input] string.
         *
         * @return a string with its first letter capitalized.
         */
        @JvmStatic
        fun capitaliseFirst(input: String): String? {
            return if (input.isEmpty()) null else input[0].uppercaseChar().toString() + input.substring(1)
                .lowercase(Locale.getDefault())
        }

        /**
         * Capitalizes the first letter of the [input] strings and joins them into a single string.
         *
         * @return a string with the first letter of every word capitalized.
         */
        @JvmStatic
        fun capitaliseFirst(vararg input: String): String? {
            val output = buildString {
                for (s in input) {
                    append(s[0].uppercaseChar()).append(s.substring(1).lowercase(Locale.getDefault()))
                        .append(" ")
                }
            }
            return if (input.isEmpty() || output.isEmpty()) null else output.substring(0, output.length - 1)
        }
    }
}
