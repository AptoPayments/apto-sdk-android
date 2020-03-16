package com.aptopayments.core.repository.card.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.card.ProvisioningData
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetProvisioningDataUseCase constructor(
    private val repository: CardRepository,
    networkHandler: NetworkHandler
) : UseCase<ProvisioningData, GetProvisioningDataUseCase.Params>(networkHandler) {
    override fun run(params: Params) =
        repository.getProvisioningData(params.cardId, params.clientAppId, params.clientDeviceId, params.walletId)

    data class Params(val cardId: String, val clientAppId: String, val clientDeviceId: String, val walletId: String)
}
