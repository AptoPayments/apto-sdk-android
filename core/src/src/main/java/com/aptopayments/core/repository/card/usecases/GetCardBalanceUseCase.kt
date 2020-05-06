package com.aptopayments.core.repository.card.usecases

import com.aptopayments.core.data.fundingsources.Balance
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository

internal class GetCardBalanceUseCase constructor(
        private val repository: CardRepository,
        networkHandler: NetworkHandler
) : UseCase<Balance, GetCardBalanceParams>(networkHandler) {
    override fun run(params: GetCardBalanceParams) = repository.getCardBalance(params)
}
