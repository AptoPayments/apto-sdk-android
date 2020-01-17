package com.aptopayments.core.repository.cardapplication.remote.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class IssueCardRequest(

        @SerializedName("application_id")
        val applicationId: String

) : Serializable
