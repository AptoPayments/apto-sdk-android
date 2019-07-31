package com.aptopayments.core.repository.card.remote.entities

import com.aptopayments.core.data.card.CardStyle
import com.aptopayments.core.extension.ColorParser
import com.google.gson.annotations.SerializedName
import java.net.MalformedURLException
import java.net.URL

const val DEFAULT_TEXT_CARD_COLOR = "FFFFFF"

internal data class CardStyleEntity(

        @SerializedName("background")
        var background: CardBackgroundStyleEntity?,

        @SerializedName("text_color")
        var textColor: String? = DEFAULT_TEXT_CARD_COLOR,

        @SerializedName("balance_selector_asset")
        var balanceSelectorAsset: String? = null
) {
    fun toCardStyle(colorParser: ColorParser): CardStyle? {
        return background?.let {
            it.toCardBackgroundStyle()?.let { cardBackgroundStyle ->
                CardStyle(background = cardBackgroundStyle,
                        textColor = colorParser.fromHexString(textColor, DEFAULT_TEXT_CARD_COLOR),
                        balanceSelectorAsset = parseAssetUrl(balanceSelectorAsset))
            }
        }
    }

    private fun parseAssetUrl(url: String?): URL? = url?.let {
        try {
            URL(it)
        }
        catch (e: MalformedURLException) {
            null
        }
    }

    companion object {
        fun from(cardStyle: CardStyle?): CardStyleEntity? {
            return cardStyle?.let {
                CardStyleEntity(background = CardBackgroundStyleEntity.from(cardStyle.background),
                        textColor = cardStyle.textColor?.toString(16),
                        balanceSelectorAsset = cardStyle.balanceSelectorAsset.toString())
            }
        }
    }
}
