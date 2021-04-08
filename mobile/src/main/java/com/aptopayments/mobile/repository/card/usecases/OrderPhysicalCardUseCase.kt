package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository

internal class OrderPhysicalCardUseCase(
    private val repository: CardRepository,
    networkHandler: NetworkHandler
) : UseCase<Card, OrderPhysicalCardUseCase.Params>(networkHandler) {

    override fun run(params: Params) = repository.orderPhysicalCard(cardId = params.cardId)

    data class Params(val cardId: String)
}
