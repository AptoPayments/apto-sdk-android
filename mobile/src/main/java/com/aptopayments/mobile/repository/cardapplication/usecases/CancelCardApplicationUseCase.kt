package com.aptopayments.mobile.repository.cardapplication.usecases

import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.cardapplication.CardApplicationRepository

internal class CancelCardApplicationUseCase constructor(
    private val applicationRepository: CardApplicationRepository,
    networkHandler: NetworkHandler
) : UseCase<Unit, String>(networkHandler) {

    override fun run(params: String) = applicationRepository.cancelCardApplication(cardApplicationId = params)
}
