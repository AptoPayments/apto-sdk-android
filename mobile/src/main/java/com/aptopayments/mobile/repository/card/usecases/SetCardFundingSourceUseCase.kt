package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository

internal class SetCardFundingSourceUseCase constructor(
    private val repository: CardRepository,
    networkHandler: NetworkHandler
) : UseCase<Balance, SetCardBalanceParams>(networkHandler) {

    override fun run(params: SetCardBalanceParams) = repository.setCardBalance(params)
}
