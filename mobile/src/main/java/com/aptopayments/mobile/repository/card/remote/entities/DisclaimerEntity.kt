package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.data.card.Disclaimer
import com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction.ContentEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class DisclaimerEntity(
    @SerializedName("agreement_keys")
    val keys: List<String>? = emptyList(),

    @SerializedName("content")
    val content: ContentEntity?
) : Serializable {
    fun toDisclaimer() =
        if (keys != null && content != null) Disclaimer(keys = keys, content = content.toContent()) else null

    companion object {
        fun from(value: Disclaimer) = DisclaimerEntity(keys = value.keys, content = ContentEntity.from(value.content))
    }
}
