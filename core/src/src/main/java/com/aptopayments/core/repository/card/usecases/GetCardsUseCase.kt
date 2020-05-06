package com.aptopayments.core.repository.card.usecases

import com.aptopayments.core.data.card.Card
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository

internal class GetCardsUseCase constructor(
        private val repository: CardRepository,
        networkHandler: NetworkHandler
) : UseCase<List<Card>, Unit>(networkHandler) {
    override fun run(params: Unit) = repository.getCards()
}
