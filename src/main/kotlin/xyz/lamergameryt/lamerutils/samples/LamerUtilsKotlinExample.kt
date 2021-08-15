package xyz.lamergameryt.lamerutils.samples


import xyz.lamergameryt.lamerutils.generic.NumberUtils
import xyz.lamergameryt.lamerutils.generic.StringUtils
import xyz.lamergameryt.lamerutils.generic.TimeUtils
import xyz.lamergameryt.lamerutils.generic.URLUtils
import xyz.lamergameryt.lamerutils.security.AESEncryptor
import xyz.lamergameryt.lamerutils.wolfram.WolframAPI
import xyz.lamergameryt.lamerutils.wolfram.WolframPod
import xyz.lamergameryt.lamerutils.wolfram.WolframSubpod

/**
 * This class contains examples for the sample usage of the library.
 */
@Suppress("unused")
class LamerUtilsKotlinExample {
    /**
     * Example usage for the [WolframAPI][xyz.lamergameryt.lamerutils.wolfram.WolframAPI] utility class.
     */
    private fun wolframAlphaExample() {
        // The equation you want to solve.
        val equation = "3x + 5"

        val response = WolframAPI.getWolframResponse(equation)
        response.pods.forEach { pod ->
            println("----------")
            println(pod.title)
            pod.subpods.forEach { subpod -> println(subpod.title + " : " + subpod.img.src) }
        }
    }

    /**
     * Example usage for the [StringUtils][xyz.lamergameryt.lamerutils.generic.StringUtils] utility class.
     */
    private fun stringUtilsExample() {
        // Creates a random alpha-numeric string of 5 characters.
        val randomString = StringUtils.getRandomString(5)
        println("Random String : $randomString")

        val text = "Hello, welcome to LamerUtils examples!"
        val base64 = StringUtils.convertToBase64(text)
        val hex = StringUtils.convertToHex(text)
        val binary = StringUtils.convertToBinary(text)

        println("Regular ASCII : $text")
        println("Base64 : $base64")
        println("Hex : $hex")
        println("Binary : $binary")
    }

    /**
     * Example usage for the [TimeUtils][xyz.lamergameryt.lamerutils.generic.TimeUtils] utility class.
     */
    private fun timeUtilsExample() {
        // The time you want to parse.
        val sampleTime = "5 days 12 hours"

        val parser = TimeUtils.TimeParser(sampleTime)
        println("$sampleTime can also be written as : ")
        println("${parser.seconds} seconds.")
        println("${parser.minutes} minutes.")
        println("${parser.hours} hours.")
        println("I can also write it shortened in HH MM SS format as ${TimeUtils.convertSecondsToText(parser.seconds)}")
    }

    /**
     * Example usage for the [URLUtils][xyz.lamergameryt.lamerutils.generic.URLUtils] utility class.
     */
    private fun urlUtilsExample() {
        // The URL to check.
        val sampleUrl = "https://lamerutils.surge.sh/"

        if (URLUtils.isUrl(sampleUrl)) {
            println("The URL provided is a valid url.")
        } else {
            println("The URL provided is invalid.")
        }
    }

    /**
     * Example usage for the [NumberUtils][xyz.lamergameryt.lamerutils.generic.NumberUtils] utility class.
     */
    private fun numberUtilsExample() {
        val number = 305030.25
        println("Choose what's more user-friendly, " + number + " or " + NumberUtils.abbreviateNumber(number))
    }

    /**
     * Example usage for the [AESEncryptor][xyz.lamergameryt.lamerutils.security.AESEncryptor] utility class.
     */
    private fun aesEncryptorExample() {
        // If no parameter is passed to the constructor then a random key is generated.
        val encryptor = AESEncryptor()
        val exampleString = "This is LamerUtils's AES Encryptor!"

        println("Without AES Encryption : $exampleString")
        println("With AES Encryption : " + encryptor.encrypt(exampleString))
    }
}
