package xyz.lamergameryt.lamerutils.generic

import org.junit.Test
import kotlin.test.assertEquals

class StringUtilsTest {
    private val testString = "Hello, I'm LamerGamerYT."

    @Test
    fun testBinary() {
        val binaryEncoded = StringUtils.convertToBinary(testString)
        assertEquals(testString, StringUtils.convertFromBinary(binaryEncoded))
    }

    @Test
    fun testHexadecimal() {
        val hexEncoded = StringUtils.convertToHex(testString)
        assertEquals(testString, StringUtils.convertFromHex(hexEncoded))
    }

    @Test
    fun testBase64() {
        val base64Encoded = StringUtils.convertToBase64(testString)
        assertEquals(testString, StringUtils.convertFromBase64(base64Encoded).decodeToString())
    }
}
