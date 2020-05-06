package com.aptopayments.core.repository.card.usecases

import com.aptopayments.core.data.card.CardDetails
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository

internal class GetCardDetailsUseCase constructor(
        private val repository: CardRepository,
        networkHandler: NetworkHandler
) : UseCase<CardDetails, String>(networkHandler) {
    override fun run(params: String) = repository.getCardDetails(params)
}
