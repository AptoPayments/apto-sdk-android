package com.aptopayments.mobile.repository.cardapplication.remote.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class AcceptDisclaimerRequest(

    @SerializedName("workflow_object_id")
    val workflowObjectId: String,

    @SerializedName("action_id")
    val actionId: String

) : Serializable
