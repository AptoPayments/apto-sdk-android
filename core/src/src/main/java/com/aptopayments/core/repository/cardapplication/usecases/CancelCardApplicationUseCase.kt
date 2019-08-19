package com.aptopayments.core.repository.cardapplication.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.cardapplication.CardApplicationRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class CancelCardApplicationUseCase constructor(
        private val applicationRepository: CardApplicationRepository,
        networkHandler: NetworkHandler
) : UseCase<Unit, String>(networkHandler) {

    override fun run(params: String) = applicationRepository.cancelCardApplication(cardApplicationId = params)
}
