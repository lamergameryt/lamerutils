@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package xyz.lamergameryt.lamerutils.wolfram

/**
 * The class used for deserializing a Wolfram pod.
 *
 * @property title the title of the pod.
 * @property error represents if there was an error while processing the pod.
 * @property numsubpods the number of subpods present in this pod.
 * @property primary represents if the current pod is the primary pod.
 * @property subpods a list of subpods present in the pod.
 */
class WolframPod(
    val title: String,
    val error: Boolean,
    val numsubpods: Int,
    val primary: Boolean,
    val subpods: List<WolframSubpod>
)

/**
 * The class used for deserializing a Wolfram subpod.
 *
 * @property title the title of the subpod.
 * @property img the image of the processed subpod.
 */
class WolframSubpod(val title: String, @get:JvmName("getImage") val img: WolframImage)

/**
 * The class used for deserializing a Wolfram image.
 *
 * @property src the direct https link to the image.
 * @property title the title of the image.
 * @property width the width of the image in pixels.
 * @property height the height of the image in pixels.
 */
class WolframImage(@get:JvmName("getLink") val src: String, val title: String, val width: Int, val height: Int)

/**
 * The class used for deserializing a Wolfram response.
 *
 * @property success represents if the [equation][xyz.lamergameryt.lamerutils.wolfram.WolframAPI.getWolframResponse]
 * was successfully processed.
 * @property error represents if there was an error occurred while processing the
 * [equation][xyz.lamergameryt.lamerutils.wolfram.WolframAPI.getWolframResponse].
 */
class WolframResponse(
    val success: Boolean,
    val error: Boolean,
    val timing: Double,
    val pods: List<WolframPod>
)
