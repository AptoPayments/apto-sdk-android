package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.content.Content
import com.google.gson.annotations.SerializedName

internal class NativeContentEntity(

    @SerializedName("format")
    val format: String,

    @SerializedName("value")
    val value: NativeValueEntity

) : ContentEntity {
    override fun toContent(): Content {
        return value.toContent()
    }
}
