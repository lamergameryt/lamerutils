package xyz.lamergameryt.lamerutils.generic

/**
 * This class contains various static utilities for URLs.
 */
@Suppress("unused")
class URLUtils {
    companion object {
        private const val URL_REGEX: String =
            "https?://(www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_+.~#?&/=]*)"

        /**
         * Checks if the [input] string entered is a valid http or https url.
         *
         * @return the result of the check (true if the string is an url)
         */
        @JvmStatic
        fun isUrl(input: String): Boolean = input.matches(Regex(URL_REGEX))
    }
}
