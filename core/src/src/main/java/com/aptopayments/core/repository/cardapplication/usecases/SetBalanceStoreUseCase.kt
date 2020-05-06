package com.aptopayments.core.repository.cardapplication.usecases

import com.aptopayments.core.data.card.SelectBalanceStoreResult
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.cardapplication.CardApplicationRepository

internal class SetBalanceStoreUseCase constructor(
        private val applicationRepository: CardApplicationRepository,
        networkHandler: NetworkHandler
) : UseCase<SelectBalanceStoreResult, SetBalanceStoreUseCase.Params>(networkHandler) {

    data class Params (
            val cardApplicationId: String,
            val tokenId: String
    )

    override fun run(params: Params) =
            applicationRepository.setBalanceStore(
                    cardApplicationId = params.cardApplicationId,
                    tokenId = params.tokenId)
}
