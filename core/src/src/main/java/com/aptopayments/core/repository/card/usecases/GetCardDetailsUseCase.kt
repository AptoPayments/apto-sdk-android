package com.aptopayments.core.repository.card.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.card.CardDetails
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetCardDetailsUseCase constructor(
        private val repository: CardRepository,
        networkHandler: NetworkHandler
) : UseCase<CardDetails, String>(networkHandler) {
    override fun run(params: String) = repository.getCardDetails(params)
}
