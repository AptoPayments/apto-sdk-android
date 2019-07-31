package com.aptopayments.core.repository.config.remote.entities

import com.aptopayments.core.data.cardproduct.CardProduct
import com.aptopayments.core.extension.ColorParser
import com.aptopayments.core.extension.ColorParserImpl
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.ContentEntity
import com.google.gson.annotations.SerializedName
import java.net.URL

internal data class CardProductEntity (

        @SerializedName("id")
        val id: String = "",

        @SerializedName("cardholder_agreement")
        val cardholderAgreement: ContentEntity? = null,

        @SerializedName("privacy_policy")
        val privacyPolicy: ContentEntity? = null,

        @SerializedName("terms_of_service")
        val termsOfService: ContentEntity? = null,

        @SerializedName("faq")
        val faq: ContentEntity? = null,

        @SerializedName ("wait_list_background_image")
        var waitlistBackgroundImage: String? = null,

        @SerializedName ("wait_list_background_color")
        var waitlistBackgroundColor: String? = null,

        @SerializedName ("wait_list_asset")
        var waitlistAsset: String? = null,

        // This is a dependency, no need to serialize or parse it
        @Transient
        var colorParser: ColorParser = ColorParserImpl()

) {
    fun toCardProduct() = CardProduct (
            id = id,
            cardholderAgreement = cardholderAgreement?.toContent(),
            privacyPolicy = privacyPolicy?.toContent(),
            termsAndConditions = termsOfService?.toContent(),
            faq = faq?.toContent(),
            waitlistBackgroundImage = waitlistBackgroundImage?.let { URL(it) },
            waitlistBackgroundColor = waitlistBackgroundColor?.let { colorParser.fromHexString(waitlistBackgroundColor, "FFFFFF") },
            waitlistAsset = waitlistAsset?.let{ URL(it) }
    )
}
