package com.aptopayments.core.repository.cardapplication.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.card.CardApplication
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.cardapplication.CardApplicationRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetCardApplicationUseCase @Inject constructor(
        private val applicationRepository: CardApplicationRepository,
        networkHandler: NetworkHandler
) : UseCase<CardApplication, String>(networkHandler) {
    override fun run(params: String) =
            applicationRepository.getCardApplication(cardApplicationId = params)
}
