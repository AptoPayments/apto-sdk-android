package com.aptopayments.core.repository.card.remote.entities

import com.google.gson.annotations.SerializedName

data class ProvisioningEntity(
    @SerializedName("push_tokenize_request_data")
    val pushTokenizeRequestData: ProvisioningDataEntity
)
