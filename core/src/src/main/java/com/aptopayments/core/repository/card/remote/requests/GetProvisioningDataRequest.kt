package com.aptopayments.core.repository.card.remote.requests

import com.google.gson.annotations.SerializedName

internal data class GetProvisioningDataRequest(
    @SerializedName("client_app_id")
    val clientAppId: String,
    @SerializedName("client_device_id")
    val clientDeviceId: String,
    @SerializedName("client_wallet_account_id")
    val walletId: String,
    @SerializedName("intent")
    val intent: String = "PUSH_PROV_MOBILE"
)

internal data class GetProvisioningDataRequestWrapper(
    @SerializedName("data")
    val data: GetProvisioningDataRequest
)
