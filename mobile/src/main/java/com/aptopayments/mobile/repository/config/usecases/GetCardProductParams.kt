package com.aptopayments.mobile.repository.config.usecases

internal data class GetCardProductParams(
    val refresh: Boolean = true,
    val cardProductId: String
)
