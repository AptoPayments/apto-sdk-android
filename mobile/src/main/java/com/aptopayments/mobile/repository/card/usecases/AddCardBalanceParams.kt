package com.aptopayments.mobile.repository.card.usecases

data class AddCardBalanceParams(
    val cardID: String,
    val fundingSourceType: String,
    val custodianType: String,
    val credentialType: String,
    val tokenId: String
)
