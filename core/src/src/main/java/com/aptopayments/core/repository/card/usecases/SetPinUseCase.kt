package com.aptopayments.core.repository.card.usecases

import com.aptopayments.core.data.card.Card
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository

internal class SetPinUseCase constructor(
        private val repository: CardRepository,
        networkHandler: NetworkHandler
) : UseCase<Card, SetPinParams>(networkHandler) {
    override fun run(params: SetPinParams) = repository.setPin(params)
}
