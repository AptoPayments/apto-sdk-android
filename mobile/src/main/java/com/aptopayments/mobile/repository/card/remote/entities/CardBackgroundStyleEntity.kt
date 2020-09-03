package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.CardBackgroundStyle
import com.aptopayments.mobile.extension.ColorParserImpl
import com.google.gson.annotations.SerializedName
import java.net.URL

private const val TYPE_IMAGE = "image"
private const val TYPE_COLOR = "color"

internal data class CardBackgroundStyleEntity(
    @SerializedName("background_type")
    val background_type: String? = "color",

    @SerializedName("background_image")
    val image: String? = null,

    @SerializedName("background_color")
    val color: String? = null,

    @SerializedName("card_logo")
    val cardLogo: String? = null
) {

    fun toCardBackgroundStyle(): CardBackgroundStyle? {
        val colorParser = ColorParserImpl()
        return when (background_type) {
            TYPE_IMAGE -> CardBackgroundStyle.Image(URL(image), getCardLogoUrl())
            TYPE_COLOR -> CardBackgroundStyle.Color(colorParser.fromHexString(color, "0567D0"), getCardLogoUrl())
            else -> null
        }
    }

    private fun getCardLogoUrl() = cardLogo?.let { URL(it) }

    companion object {
        fun from(value: CardBackgroundStyle?): CardBackgroundStyleEntity? {
            return when (value) {
                is CardBackgroundStyle.Image ->
                    CardBackgroundStyleEntity(
                        background_type = TYPE_IMAGE,
                        image = value.url.toString(),
                        cardLogo = value.logo?.toString()
                    )
                is CardBackgroundStyle.Color ->
                    CardBackgroundStyleEntity(
                        background_type = TYPE_COLOR,
                        color = value.color.toString(16),
                        cardLogo = value.logo?.toString()
                    )
                else -> null
            }
        }
    }
}
