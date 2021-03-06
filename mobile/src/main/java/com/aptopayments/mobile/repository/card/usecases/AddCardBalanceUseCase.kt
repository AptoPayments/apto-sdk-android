package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository

internal class AddCardBalanceUseCase(
    private val repository: CardRepository,
    networkHandler: NetworkHandler
) : UseCase<Balance, AddCardBalanceParams>(networkHandler) {

    override fun run(params: AddCardBalanceParams) = repository.addCardBalance(params)
}
