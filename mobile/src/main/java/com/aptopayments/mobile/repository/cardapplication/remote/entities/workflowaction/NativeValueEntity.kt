package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.content.Content
import com.aptopayments.mobile.extension.ColorParser
import com.aptopayments.mobile.extension.ColorParserImpl
import com.aptopayments.mobile.extension.toUrl
import com.google.gson.annotations.SerializedName

internal class NativeValueEntity(

    @SerializedName("background_color")
    val backgroundColor: String? = null,

    @SerializedName("background_image")
    val backgroundImage: String? = null,

    @SerializedName("asset")
    val asset: String? = null
) : ContentEntity {

    private val colorParser: ColorParser = ColorParserImpl()

    override fun toContent() = Content.Native(
        backgroundColor = backgroundColor?.let { colorParser.fromHexString(backgroundColor, "FFFFFF") },
        backgroundImage = backgroundImage?.toUrl(),
        asset = asset?.toUrl()
    )
}
