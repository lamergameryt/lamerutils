package xyz.lamergameryt.lamerutils.wolfram

import com.google.gson.Gson
import kong.unirest.*
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.*

/**
 * This class is used to query the Wolfram|Alpha API without the need of an application id.
 */
class WolframAPI {
    companion object {
        private const val userAgent = "Wolfram Android App"
        private const val appId = "3H4296-5YPAGQUJK7"
        private const val apiUrl = "https://api.wolframalpha.com/v2/query.jsp"
        private const val salt = "vFdeaRwBTVqdc5CL"

        /**
         * Sends [equation] to the Wolfram|Alpha servers and deserialize the response in the form of
         * a [WolframResponse][xyz.lamergameryt.lamerutils.wolfram.WolframResponse] class.
         */
        @JvmStatic
        fun getWolframResponse(equation: String): WolframResponse = Gson().fromJson(
            getResponse(equation).body.getObject().getJSONObject("queryresult").toString(),
            WolframResponse::class.java
        )

        /**
         * Send a GET request to the [WolframAPI][apiUrl] with the corresponding [equation].
         *
         * @return a HttpResponse containing the JSON response.
         */
        private fun getResponse(equation: String): HttpResponse<JsonNode> {
            val params = getParams(equation)
            val request: HttpRequest<GetRequest> = Unirest.get(apiUrl).header("User-Agent", userAgent)
            for (key in params.keys) request.queryString(key, params[key])
            return request.asJson()
        }

        /**
         * Calculate the MD5 hash signature of the [query params][params] with the [salt].
         *
         * @return the hash of the parameters.
         */
        private fun getSignature(params: HashMap<String, String>): String {
            fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }

            val signature = StringBuilder(salt)
            for (key in TreeSet(params.keys)) {
                signature.append(key).append(encodeToUrl(params[key]))
            }

            val md = MessageDigest.getInstance("MD5")
            val digest = md.digest(signature.toString().toByteArray(StandardCharsets.UTF_8))
            return digest.toHex().uppercase()
        }

        /**
         * Encodes the [value] passed to a URL friendly format.
         *
         * @return the encoded string url.
         */
        private fun encodeToUrl(value: String?): String = URLEncoder.encode(value, StandardCharsets.UTF_8.toString())

        /**
         * Make a HashMap of parameters based on the [input] given.
         *
         * @return the params HashMap.
         */
        private fun getParams(input: String): HashMap<String, String> {
            val params = HashMap<String, String>()
            params["appid"] = appId
            params["format"] = "image"
            params["output"] = "json"
            params["podstate"] = "Solution__Step-by-step solution"
            params["input"] = input
            params["sig"] = getSignature(params)

            return params
        }
    }
}
