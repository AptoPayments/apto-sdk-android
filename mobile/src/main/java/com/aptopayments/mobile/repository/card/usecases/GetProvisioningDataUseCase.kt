package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.card.ProvisioningData
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository

internal class GetProvisioningDataUseCase(
    private val repository: CardRepository,
    networkHandler: NetworkHandler
) : UseCase<ProvisioningData, GetProvisioningDataUseCase.Params>(networkHandler) {
    override fun run(params: Params) =
        repository.getProvisioningData(params.cardId)

    data class Params(val cardId: String)
}
