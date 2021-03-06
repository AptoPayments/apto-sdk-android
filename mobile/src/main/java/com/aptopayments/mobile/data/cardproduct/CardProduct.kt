package com.aptopayments.mobile.data.cardproduct

import com.aptopayments.mobile.data.content.Content
import java.io.Serializable
import java.net.URL

data class CardProduct(
    val id: String = "",
    val cardholderAgreement: Content? = null,
    val privacyPolicy: Content? = null,
    val termsAndConditions: Content? = null,
    val faq: Content? = null,
    val name: String = "",
    val waitlistBackgroundImage: URL? = null,
    val waitlistBackgroundColor: Int? = null,
    val waitlistDarkBackgroundColor: Int? = null,
    val waitlistAsset: URL? = null,
    val exchangeRates: Content? = null
) : Serializable
