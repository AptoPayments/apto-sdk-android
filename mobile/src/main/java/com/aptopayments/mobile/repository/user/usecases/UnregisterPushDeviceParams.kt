package com.aptopayments.mobile.repository.user.usecases

internal data class UnregisterPushDeviceParams(
    val userToken: String,
    val pushToken: String
)
