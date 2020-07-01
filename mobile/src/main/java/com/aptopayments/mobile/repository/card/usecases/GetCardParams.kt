package com.aptopayments.mobile.repository.card.usecases

data class GetCardParams(
    val cardId: String,
    val refresh: Boolean = true
)
