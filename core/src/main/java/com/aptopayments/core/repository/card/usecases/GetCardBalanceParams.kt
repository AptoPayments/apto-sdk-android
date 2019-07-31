package com.aptopayments.core.repository.card.usecases

data class GetCardBalanceParams (
        val cardID: String,
        val refresh: Boolean = true
)
