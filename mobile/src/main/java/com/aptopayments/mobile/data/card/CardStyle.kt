package com.aptopayments.mobile.data.card

import java.io.Serializable
import java.net.URL

sealed class CardBackgroundStyle : Serializable {
    class Image(val url: URL) : CardBackgroundStyle()
    class Color(val color: Int) : CardBackgroundStyle()
}

data class CardStyle(
    var background: CardBackgroundStyle,
    var textColor: Int?,
    var balanceSelectorAsset: URL?
) : Serializable
