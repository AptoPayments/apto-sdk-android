package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.content.Content
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class NativeContentEntity(
    @SerializedName("format")
    val format: String? = "",

    @SerializedName("value")
    val value: NativeValueEntity

) : ContentEntity, Serializable {
    override fun toContent(): Content {
        return value.toContent()
    }

    companion object {
        fun from(value: Content.Native) = NativeContentEntity(value = NativeValueEntity.from(value))
    }
}
