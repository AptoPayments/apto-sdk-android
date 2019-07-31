package com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.core.data.content.Content
import com.google.gson.annotations.SerializedName

class NativeContentEntity (

        @SerializedName("format")
        var format: String,

        @SerializedName("value")
        var value: NativeValueEntity

) : ContentEntity {
    override fun toContent(): Content {
        return value.toContent()
    }
}

