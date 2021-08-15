@file:Suppress("MemberVisibilityCanBePrivate")

package xyz.lamergameryt.lamerutils.security

import java.util.*
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

/**
 * This class is used for AES 256-bit encryption and decryption.
 *
 * @sample xyz.lamergameryt.lamerutils.samples.LamerUtilsKotlinExample.aesEncryptorExample
 */
@Suppress("unused")
class AESEncryptor {
    private val keyType = "AES"
    private val cipherType = "AES/ECB/PKCS5Padding"

    /**
     * The secret key which will be used for encryption and decryption.
     */
    var secretKey: SecretKey
        private set

    /**
     * Create an instance of the class with a random 256-bit [secretKey].
     */
    constructor() {
        val generator: KeyGenerator = KeyGenerator.getInstance(keyType)
        generator.init(256)
        secretKey = generator.generateKey()
    }

    /**
     * Create an instance of the class with [secretKey] as [key].
     */
    constructor(key: ByteArray) {
        secretKey = SecretKeySpec(key, keyType)
    }

    /**
     * Create an instance of the class with [secretKey] as [key].
     */
    constructor(key: SecretKey) {
        secretKey = key
    }

    /**
     * Encrypts the [input][str] string using AES-256 and the [secretKey].
     *
     * @return The encrypted string.
     */
    fun encrypt(str: String): String {
        val cipher = Cipher.getInstance(cipherType)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val encryptedBytes = cipher.doFinal(str.toByteArray())
        val encodedBytes = Base64.getEncoder().encode(encryptedBytes)

        return String(encodedBytes)
    }

    /**
     * Decrypts the [input][str] string encrypted using AES-256 and the [secretKey].
     *
     * @return The decrypted string.
     */
    fun decrypt(str: String): String {
        val cipher = Cipher.getInstance(cipherType)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        val decoded = Base64.getDecoder().decode(str.toByteArray())
        val decipheredBytes = cipher.doFinal(decoded)

        return String(decipheredBytes)
    }
}
