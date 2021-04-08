package com.aptopayments.mobile.data.card

import java.io.Serializable
import java.net.URL

sealed class CardBackgroundStyle : Serializable {
    abstract val logo: URL?

    data class Image(val url: URL, override val logo: URL?) : CardBackgroundStyle()
    data class Color(val color: Int, override val logo: URL?) : CardBackgroundStyle()
}

data class CardStyle(
    var background: CardBackgroundStyle,
    var textColor: Int?,
    var balanceSelectorAsset: URL?
) : Serializable
