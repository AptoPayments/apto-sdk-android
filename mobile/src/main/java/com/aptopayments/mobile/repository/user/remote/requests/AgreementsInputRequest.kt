package com.aptopayments.mobile.repository.user.remote.requests

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class AgreementsInputRequest(
    @SerializedName("agreements_keys")
    val keys: List<String>,
    @SerializedName("user_action")
    val action: String
) : Serializable
