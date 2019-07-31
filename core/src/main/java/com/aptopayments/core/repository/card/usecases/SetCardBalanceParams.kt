package com.aptopayments.core.repository.card.usecases

data class SetCardBalanceParams (
        val cardID: String,
        val fundingSourceID: String
)
