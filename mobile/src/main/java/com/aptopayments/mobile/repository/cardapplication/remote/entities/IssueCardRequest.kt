package com.aptopayments.mobile.repository.cardapplication.remote.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class IssueCardRequest(

    @SerializedName("application_id")
    val applicationId: String,

    @SerializedName("additional_fields")
    val additionalFields: Map<String, Any>? = null,

    @SerializedName("metadata")
    val metadata: String? = null,

    @SerializedName("design")
    val design: IssueCardDesignRequest? = null,

) : Serializable
