package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.content.Content
import com.aptopayments.mobile.extension.ColorParser
import com.aptopayments.mobile.extension.ColorParserImpl
import com.aptopayments.mobile.extension.toUrl
import com.google.gson.annotations.SerializedName

internal class NativeValueEntity(

    @SerializedName("background_color")
    var backgroundColor: String? = null,

    @SerializedName("background_image")
    var backgroundImage: String? = null,

    @SerializedName("asset")
    var asset: String? = null,

    // This is a dependency, no need to serialize or parse it
    @Transient
    var colorParser: ColorParser = ColorParserImpl()

) : ContentEntity {
    override fun toContent() = Content.Native(
        backgroundColor = backgroundColor?.let { colorParser.fromHexString(backgroundColor, "FFFFFF") },
        backgroundImage = backgroundImage?.toUrl(),
        asset = asset?.toUrl()
    )
}
