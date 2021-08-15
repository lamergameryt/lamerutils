package xyz.lamergameryt.lamerutils.security

import org.junit.Test
import kotlin.test.assertEquals

internal class AESTest {
    private val encryptor = AESEncryptor()

    @Test
    fun testAES() {
        val testString = "Hello, I am a test string."
        val encrypted = encryptor.encrypt(testString)
        assertEquals(testString, encryptor.decrypt(encrypted))
    }
}
