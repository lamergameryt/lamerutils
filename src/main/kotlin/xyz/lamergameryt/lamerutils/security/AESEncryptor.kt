@file:Suppress("MemberVisibilityCanBePrivate")

package xyz.lamergameryt.lamerutils.security

import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * This class is used for AES 256-bit encryption and decryption.
 */
@Suppress("unused")
class AESEncryptor {
    private val keyType = "AES"
    private val cipherType = "AES/ECB/PKCS5Padding"

    var secretKey: SecretKey
        private set

    /**
     * Generates a random 256-bit [secretKey].
     */
    constructor() {
        val generator: KeyGenerator = KeyGenerator.getInstance(keyType)
        generator.init(256)
        secretKey = generator.generateKey()
    }

    constructor(key: ByteArray) {
        secretKey = SecretKeySpec(key, keyType)
    }

    constructor(key: SecretKey) {
        secretKey = key
    }

    /**
     * Encrypts the [input][str] string using AES-256 and the [secretKey].
     */
    fun encrypt(str: String): String {
        val cipher = Cipher.getInstance(cipherType)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val encryptedBytes = cipher.doFinal(str.toByteArray())
        val encodedBytes = Base64.getEncoder().encode(encryptedBytes)

        return String(encodedBytes)
    }

    /**
     * Decrypts the [input][str] string encrypted using AES-256 and the same [secretKey].
     */
    fun decrypt(str: String): String {
        val cipher = Cipher.getInstance(cipherType)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        val decoded = Base64.getDecoder().decode(str.toByteArray())
        val decipheredBytes = cipher.doFinal(decoded)

        return String(decipheredBytes)
    }
}
