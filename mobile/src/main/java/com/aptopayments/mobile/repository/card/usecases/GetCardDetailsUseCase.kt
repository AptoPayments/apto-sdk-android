package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.card.CardDetails
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository

internal class GetCardDetailsUseCase(
    private val repository: CardRepository,
    networkHandler: NetworkHandler
) : UseCase<CardDetails, String>(networkHandler) {
    override fun run(params: String) = repository.getCardDetails(params)
}
