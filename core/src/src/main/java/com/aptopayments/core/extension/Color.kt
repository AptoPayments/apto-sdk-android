package com.aptopayments.core.extension

import android.graphics.Color
import androidx.annotation.ColorInt

interface ColorParser {
    fun fromHexString(colorString: String?, default: String): Int
    fun fromHexString(colorString: String): Int
}

class ColorParserImpl: ColorParser {
    override fun fromHexString(colorString: String?, default: String): Int {
        return try {
            Color.parseColor("#$colorString")
        } catch (exception: Throwable) {
            Color.parseColor("#$default")
        }
    }

    override fun fromHexString(colorString: String): Int {
        return try {
            Color.parseColor("#$colorString")
        } catch (exception: Throwable) {
            Color.WHITE
        }
    }
}

fun adjustColorValue(@ColorInt color: Int, value: Float): Int {
    val hsv = FloatArray(3)
    Color.colorToHSV(color, hsv)
    hsv[2] *= value
    return Color.HSVToColor(hsv)
}

fun isDarkColor(@ColorInt color: Int): Boolean {
    val red = Color.red(color)
    val green = Color.green(color)
    val blue = Color.blue(color)
    return getBrightness(red, green, blue) <= 200
}

fun getBrightness(red: Int, green: Int, blue: Int): Int {
    // As per http://stackoverflow.com/a/2241471
    return Math.sqrt(red.toDouble() * red.toDouble() * .299 +
            green.toDouble() * green.toDouble() * .587 +
            blue.toDouble() * blue.toDouble() * .114).toInt()
}
