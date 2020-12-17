package com.aptopayments.mobile.repository.card.remote.requests

import com.google.gson.annotations.SerializedName

internal data class SetPasscodeRequest(
    @SerializedName("passcode")
    val passcode: String,
    @SerializedName("verification_id")
    val verificationId: String?
)
