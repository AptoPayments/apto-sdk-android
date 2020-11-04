package com.aptopayments.mobile.repository.cardapplication.usecases

import com.aptopayments.mobile.data.card.CardApplication
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.cardapplication.CardApplicationRepository

internal class StartCardApplicationUseCase constructor(
    private val applicationRepository: CardApplicationRepository,
    networkHandler: NetworkHandler
) : UseCase<CardApplication, StartCardApplicationParams>(networkHandler) {
    override fun run(params: StartCardApplicationParams) =
        applicationRepository.startCardApplication(cardProductId = params.cardProductId)
}

data class StartCardApplicationParams(val cardProductId: String)
