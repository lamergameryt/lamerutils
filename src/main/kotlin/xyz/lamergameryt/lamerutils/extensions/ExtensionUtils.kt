package xyz.lamergameryt.lamerutils.extensions

/**
 * This function converts the [ByteArray] to a hexadecimal string.
 *
 * @return The hexadecimal string.
 */
fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
