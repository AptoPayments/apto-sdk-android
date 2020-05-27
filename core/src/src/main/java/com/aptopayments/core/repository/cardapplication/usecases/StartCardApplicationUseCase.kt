package com.aptopayments.core.repository.cardapplication.usecases

import com.aptopayments.core.data.card.CardApplication
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.cardapplication.CardApplicationRepository

internal class StartCardApplicationUseCase constructor(
    private val applicationRepository: CardApplicationRepository,
    networkHandler: NetworkHandler
) : UseCase<CardApplication, String>(networkHandler) {
    override fun run(params: String) =
        applicationRepository.startCardApplication(cardProductId = params)
}
