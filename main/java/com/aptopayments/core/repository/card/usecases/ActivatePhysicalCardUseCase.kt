package com.aptopayments.core.repository.card.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.card.ActivatePhysicalCardResult
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class ActivatePhysicalCardUseCase constructor(
        private val repository: CardRepository,
        networkHandler: NetworkHandler
) : UseCase<ActivatePhysicalCardResult, ActivatePhysicalCardUseCase.Params>(networkHandler) {

    override fun run(params: Params) = repository.activatePhysicalCard(params.cardId, params.code)

    data class Params (
            val cardId: String,
            val code: String
    )
}
