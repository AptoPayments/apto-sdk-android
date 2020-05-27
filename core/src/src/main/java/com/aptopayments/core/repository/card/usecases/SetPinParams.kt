package com.aptopayments.core.repository.card.usecases

data class SetPinParams(
    val cardId: String,
    val pin: String
)
