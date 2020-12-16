package com.aptopayments.mobile.repository.cardapplication.usecases

import com.aptopayments.mobile.data.card.CardApplication
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.cardapplication.CardApplicationRepository

internal class GetCardApplicationUseCase(
    private val applicationRepository: CardApplicationRepository,
    networkHandler: NetworkHandler
) : UseCase<CardApplication, String>(networkHandler) {
    override fun run(params: String) =
        applicationRepository.getCardApplication(cardApplicationId = params)
}
