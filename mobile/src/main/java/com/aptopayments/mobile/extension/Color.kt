package com.aptopayments.mobile.extension

import android.graphics.Color
import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
interface ColorParser {
    fun fromHexString(colorString: String?, default: String): Int
    fun fromHexString(colorString: String): Int
}

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
class ColorParserImpl : ColorParser {
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
