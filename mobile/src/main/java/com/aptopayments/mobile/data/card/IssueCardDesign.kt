package com.aptopayments.mobile.data.card

/**
 * Define a particular design for the card, the positioning of the elements need to be manually configured.
 * Please be in touch to configure it together.
 * @param designKey Defines the identifier of the design to be applied to the card.
 * @param qrCode  Defines the URL for the QR code image to be printed in the card.
 * @param extraEmbossingLine Defines the extra-embossing line content on the card (under the cardholder name).
 * @param imageUrl Defines a main image to be inserted to the card.
 * @param additionalImageUrl Defines an additional (secondary) image to be inserted to the card.
 */
data class IssueCardDesign(
    val designKey: String? = null,
    val qrCode: String? = null,
    val extraEmbossingLine: String? = null,
    val imageUrl: String? = null,
    val additionalImageUrl: String? = null
)
