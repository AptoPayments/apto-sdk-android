package com.aptopayments.mobile.data.card

import java.io.Serializable
import java.net.URL

sealed class CardBackgroundStyle(val logo: URL?) : Serializable {
    class Image(val url: URL, logo: URL?) : CardBackgroundStyle(logo)
    class Color(val color: Int, logo: URL?) : CardBackgroundStyle(logo)
}

data class CardStyle(
    var background: CardBackgroundStyle,
    var textColor: Int?,
    var balanceSelectorAsset: URL?
) : Serializable
