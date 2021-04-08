package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.card.OrderPhysicalCardConfig
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository

internal class GetOrderPhysicalCardConfigurationUseCase(
    private val repository: CardRepository,
    networkHandler: NetworkHandler
) : UseCase<OrderPhysicalCardConfig, GetOrderPhysicalCardConfigurationUseCase.Params>(networkHandler) {

    override fun run(params: Params) = repository.getOrderPhysicalCardConfig(cardId = params.cardId)

    data class Params(val cardId: String)
}
