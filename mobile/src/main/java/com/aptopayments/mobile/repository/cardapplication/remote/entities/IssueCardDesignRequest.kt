package com.aptopayments.mobile.repository.cardapplication.remote.entities

import com.aptopayments.mobile.data.card.IssueCardDesign
import com.google.gson.annotations.SerializedName

internal class IssueCardDesignRequest(
    @SerializedName("design_key")
    val designKey: String? = null,

    @SerializedName("qr_code")
    val qrCode: String? = null,

    @SerializedName("extra_embossing_line")
    val extraEmbossingLine: String? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("additional_image_url")
    val additionalImageUrl: String? = null
) {
    companion object {
        fun from(design: IssueCardDesign) = IssueCardDesignRequest(
            designKey = design.designKey,
            qrCode = design.qrCode,
            extraEmbossingLine = design.extraEmbossingLine,
            imageUrl = design.imageUrl,
            additionalImageUrl = design.additionalImageUrl,
        )
    }
}
