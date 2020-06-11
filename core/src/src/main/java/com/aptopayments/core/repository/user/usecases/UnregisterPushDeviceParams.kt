package com.aptopayments.core.repository.user.usecases

internal data class UnregisterPushDeviceParams(
    val userToken: String,
    val pushToken: String
)
