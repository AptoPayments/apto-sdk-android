package com.aptopayments.core.repository.card.usecases

data class GetCardParams (
        val cardId: String,
        val showDetails: Boolean,
        val refresh: Boolean = true
)
