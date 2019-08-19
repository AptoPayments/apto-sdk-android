package com.aptopayments.core.repository.cardapplication.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.cardapplication.CardApplicationRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class AcceptDisclaimerUseCase constructor(
        private val applicationRepository: CardApplicationRepository,
        networkHandler: NetworkHandler
) : UseCase<Unit, AcceptDisclaimerUseCase.Params>(networkHandler) {

    data class Params (
            val workflowObjectId: String,
            val actionId: String
    )

    override fun run(params: Params) = applicationRepository.acceptDisclaimer(
            workflowObjectId = params.workflowObjectId,
            actionId = params.actionId)
}
