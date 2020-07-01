package com.aptopayments.mobile.repository.card.usecases

data class SetCardBalanceParams(
    val cardID: String,
    val fundingSourceID: String
)
