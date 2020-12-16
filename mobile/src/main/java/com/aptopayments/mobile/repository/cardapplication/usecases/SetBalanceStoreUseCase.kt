package com.aptopayments.mobile.repository.cardapplication.usecases

import com.aptopayments.mobile.data.card.SelectBalanceStoreResult
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.cardapplication.CardApplicationRepository

internal class SetBalanceStoreUseCase(
    private val applicationRepository: CardApplicationRepository,
    networkHandler: NetworkHandler
) : UseCase<SelectBalanceStoreResult, SetBalanceStoreUseCase.Params>(networkHandler) {

    data class Params(
        val cardApplicationId: String,
        val tokenId: String
    )

    override fun run(params: Params) =
        applicationRepository.setBalanceStore(
            cardApplicationId = params.cardApplicationId,
            tokenId = params.tokenId
        )
}
