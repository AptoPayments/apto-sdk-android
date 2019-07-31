package com.aptopayments.core.repository.cardapplication.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.card.SelectBalanceStoreResult
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.cardapplication.CardApplicationRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class SetBalanceStoreUseCase @Inject constructor(
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
