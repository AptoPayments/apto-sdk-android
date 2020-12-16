package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.card.ActivatePhysicalCardResult
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository

internal class ActivatePhysicalCardUseCase(
    private val repository: CardRepository,
    networkHandler: NetworkHandler
) : UseCase<ActivatePhysicalCardResult, ActivatePhysicalCardUseCase.Params>(networkHandler) {

    override fun run(params: Params) = repository.activatePhysicalCard(params.cardId, params.code)

    data class Params(
        val cardId: String,
        val code: String
    )
}
