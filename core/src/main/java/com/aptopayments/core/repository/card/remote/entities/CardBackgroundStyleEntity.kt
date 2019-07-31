package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.data.card.CardBackgroundStyle
import com.aptopayments.core.extension.ColorParser
import com.aptopayments.core.extension.ColorParserImpl
import com.google.gson.annotations.SerializedName
import java.net.URL

internal data class CardBackgroundStyleEntity (

        @SerializedName ("background_type")
        var background_type: String? = "color",

        @SerializedName ("background_image")
        var image: String? = null,

        @SerializedName ("background_color")
        var color: String? = null,

        // This is a dependency, no need to serialize or parse it
        @Transient
        var colorParser: ColorParser = ColorParserImpl()

) {
    fun toCardBackgroundStyle(): CardBackgroundStyle? {
        return when (background_type) {
            "image" -> CardBackgroundStyle.Image(URL(image))
            "color" -> CardBackgroundStyle.Color(colorParser.fromHexString(color, "0567D0"))
            else -> null
        }
    }

    companion object {
        fun from(cardBackgroundStyle: CardBackgroundStyle?): CardBackgroundStyleEntity? {
            return when (cardBackgroundStyle) {
                is CardBackgroundStyle.Image ->
                    CardBackgroundStyleEntity(
                            background_type = "image",
                            image = cardBackgroundStyle.url.toString()
                    )
                is CardBackgroundStyle.Color ->
                    CardBackgroundStyleEntity(
                            background_type = "color",
                            color = cardBackgroundStyle.color.toString(16)
                    )
                else -> null
            }
        }
    }
}
