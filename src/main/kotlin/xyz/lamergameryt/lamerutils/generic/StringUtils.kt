package xyz.lamergameryt.lamerutils.generic

import xyz.lamergameryt.lamerutils.extensions.toHex
import java.security.SecureRandom
import java.util.*
import java.util.stream.Collectors


/**
 * This class contains various static utilities for strings.
 *
 * @sample xyz.lamergameryt.lamerutils.samples.LamerUtilsKotlinExample.stringUtilsExample
 */
@Suppress("unused", "SpellCheckingInspection")
class StringUtils {
    /**
     * This object defines all the static variables and methods present in [StringUtils].
     */
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
         * @return A random alphanumeric string.
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
         * @return A string with its first letter capitalized.
         */
        @JvmStatic
        fun capitaliseFirst(input: String): String {
            return if (input.isEmpty()) "" else input[0].uppercaseChar().toString() + input.substring(1)
                .lowercase(Locale.getDefault())
        }

        /**
         * Capitalizes the first letter of the [input] strings and joins them into a single string.
         *
         * @return A string with the first letter of every word capitalized.
         */
        @JvmStatic
        fun capitaliseFirst(vararg input: String): String {
            if (input.isEmpty())
                return ""

            val output = buildString {
                for (s in input) {
                    append(s[0].uppercaseChar()).append(s.substring(1).lowercase(Locale.getDefault()))
                        .append(" ")
                }
            }
            return if (output.isEmpty()) "" else output.substring(0, output.length - 1)
        }

        private fun convertToUnformattedBinary(input: String): String {
            val result = buildString {
                val chars = input.toCharArray()
                for (c in chars) {
                    append(String.format("%8s", Integer.toBinaryString(c.code)).replace(" ".toRegex(), "0"))
                }
            }
            return result
        }

        /**
         * Convertes the [input] string to binary format.
         *
         * Note: Only ASCII values are supported.
         *
         * @return The [input] string encoded as a binary string.
         */
        @JvmStatic
        fun convertToBinary(input: String): String {
            val binary = convertToUnformattedBinary(input)
            val blockSize = 8
            val seperator = " "

            val result = arrayListOf<String>()
            var index = 0
            while (index < binary.length) {
                result.add(binary.substring(index, (index + blockSize).coerceAtMost(binary.length)))
                index += blockSize
            }

            return result.stream().collect(Collectors.joining(seperator))
        }

        /**
         * Converts the [input] string to a UTF-8 string.
         *
         * Note: If [input] is not a valid binary string then a [java.lang.NumberFormatException] will be thrown.
         *
         * @return The decoded UTF-8 string.
         */
        @JvmStatic
        fun convertFromBinary(input: String): String {
            return input.split(" ").map { binary -> binary.toInt(2).toChar() }.joinToString("")
        }

        /**
         * Converts the [input] string to a hexadecimal string.
         *
         * Note: Only ASCII values are supported.
         *
         * @return The encoded hexadecimal string.
         */
        @JvmStatic
        fun convertToHex(input: String): String = convertToHex(input.toByteArray())

        /**
         * Converts the [input] byte array to a hexadecimal string.
         *
         * Note: Only ASCII values are supported.
         *
         * @return The encoded hexadecimal string.
         */
        @JvmStatic
        fun convertToHex(input: ByteArray): String = input.toHex()

        /**
         * Converts the [input] hexadecimal string to an ASCII string.
         *
         * Note: If [input] is not a valid hexadecimal string then a [java.lang.NumberFormatException] will be thrown
         *
         * @return The decoded ASCII string.
         */
        @JvmStatic
        fun convertFromHex(input: String): String {
            val output = buildString {
                var i = 0
                while (i < input.length) {
                    val str = input.substring(i, i + 2)
                    append(str.toInt(16).toChar())
                    i += 2
                }
            }
            return output
        }

        /**
         * Converts the [input] UTF-8 string to a Base64 encoded string.
         *
         * @return The encoded Base64 string.
         */
        @JvmStatic
        fun convertToBase64(input: String): String = convertToBase64(input.toByteArray())

        /**
         * Converts the [input] byte array to a Base64 encoded string.
         *
         * @return The encoded Base64 string.
         */
        @JvmStatic
        fun convertToBase64(input: ByteArray): String = Base64.getEncoder().encodeToString(input)

        /**
         * Converts the [input] Base64 encoded string to a byte array.
         *
         * @return The decoded Base64 byte array.
         */
        @JvmStatic
        fun convertFromBase64(input: String): ByteArray = Base64.getDecoder().decode(input)
    }
}
