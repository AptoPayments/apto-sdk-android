package com.aptopayments.mobile.repository.card.usecases

data class SetPinParams(
    val cardId: String,
    val pin: String
)
