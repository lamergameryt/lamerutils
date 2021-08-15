package xyz.lamergameryt.lamerutils.samples;

import xyz.lamergameryt.lamerutils.generic.NumberUtils;
import xyz.lamergameryt.lamerutils.generic.StringUtils;
import xyz.lamergameryt.lamerutils.generic.TimeUtils;
import xyz.lamergameryt.lamerutils.generic.URLUtils;
import xyz.lamergameryt.lamerutils.security.AESEncryptor;
import xyz.lamergameryt.lamerutils.wolfram.WolframAPI;
import xyz.lamergameryt.lamerutils.wolfram.WolframResponse;

/**
 * This class contains examples for the sample usage of the library.
 */
@SuppressWarnings("unused")
public class LamerUtilsJavaExample {
    /**
     * Example usage for the {@link WolframAPI} utility class.
     */
    private void wolframAlphaExample() {
        // The equation you want to solve.
        String equation = "3x + 5";

        WolframResponse response = WolframAPI.getWolframResponse(equation);
        response.getPods().forEach(pod -> {
            System.out.println("----------");
            System.out.println(pod.getTitle());
            pod.getSubpods().forEach(subpod -> System.out.println(subpod.getTitle() + " : " + subpod.getImage().getLink()));
        });
    }

    /**
     * Example usage for the {@link StringUtils} utility class.
     */
    private void stringUtilsExample() {
        // Creates a random alpha-numeric string of 5 characters.
        String randomString = StringUtils.getRandomString(5);
        System.out.println("Random String : " + randomString);

        String text = "Hello, welcome to LamerUtils examples!";
        String base64 = StringUtils.convertToBase64(text);
        String hex = StringUtils.convertToHex(text);
        String binary = StringUtils.convertToBinary(text);

        System.out.println("Regular ASCII : " + text);
        System.out.println("Base64 : " + base64);
        System.out.println("Hex : " + hex);
        System.out.println("Binary : " + binary);
    }

    /**
     * Example usage for the {@link TimeUtils} utility class.
     */
    private void timeUtilsExample() {
        // The time you want to parse.
        String sampleTime = "5 days 12 hours";

        TimeUtils.TimeParser parser = new TimeUtils.TimeParser(sampleTime);
        System.out.println(sampleTime + " can also be written as : ");
        System.out.println(parser.getSeconds() + " seconds.");
        System.out.println(parser.getMinutes() + " minutes.");
        System.out.println(parser.getHours() + " hours.");

        System.out.println("\nI can also write it shortened in HH MM SS format as " +
                TimeUtils.convertSecondsToText(parser.getSeconds()));
    }

    /**
     * Example usage for the {@link URLUtils} utility class.
     */
    private void urlUtilsExample() {
        // The URL to check.
        String sampleUrl = "https://lamerutils.surge.sh/";

        if (URLUtils.isUrl(sampleUrl)) {
            System.out.println("The URL provided is a valid url.");
        } else {
            System.out.println("The URL provided is invalid.");
        }
    }

    /**
     * Example usage for the {@link NumberUtils} utility class.
     */
    private void numberUtilsExample() {
        double number = 305030.25;
        System.out.println("Choose what's more user-friendly, " + number + " or " + NumberUtils.abbreviateNumber(number));
    }

    /**
     * Example usage for the {@link AESEncryptor} utility class.
     */
    private void aesEncryptorExample() {
        // If no parameter is passed to the constructor then a random key is generated.
        AESEncryptor encryptor = new AESEncryptor();
        String exampleString = "This is LamerUtils's AES Encryptor!";

        System.out.println("Without AES Encryption : " + exampleString);
        System.out.println("With AES Encryption : " + encryptor.encrypt(exampleString));
    }
}
