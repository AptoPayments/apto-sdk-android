package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository

internal class GetCardsUseCase constructor(
    private val repository: CardRepository,
    networkHandler: NetworkHandler
) : UseCase<List<Card>, Unit>(networkHandler) {
    override fun run(params: Unit) = repository.getCards()
}
