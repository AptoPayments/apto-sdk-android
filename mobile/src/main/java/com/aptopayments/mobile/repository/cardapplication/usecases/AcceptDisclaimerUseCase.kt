package com.aptopayments.mobile.repository.cardapplication.usecases

import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.cardapplication.CardApplicationRepository

internal class AcceptDisclaimerUseCase(
    private val applicationRepository: CardApplicationRepository,
    networkHandler: NetworkHandler
) : UseCase<Unit, AcceptDisclaimerUseCase.Params>(networkHandler) {

    data class Params(
        val workflowObjectId: String,
        val actionId: String
    )

    override fun run(params: Params) = applicationRepository.acceptDisclaimer(
        workflowObjectId = params.workflowObjectId,
        actionId = params.actionId
    )
}
