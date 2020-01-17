package com.aptopayments.core.repository.config.usecases

data class GetCardProductParams (
        val refresh: Boolean = true,
        val cardProductId: String
)
