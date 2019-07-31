package com.aptopayments.core.repository.cardapplication.remote.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AcceptDisclaimerRequest(

        @SerializedName("workflow_object_id")
        val workflowObjectId: String,

        @SerializedName("action_id")
        val actionId: String

) : Serializable
