package com.aptopayments.core.repository.user.usecases

data class UnregisterPushDeviceParams(
    val userToken: String,
    val pushToken: String
)
