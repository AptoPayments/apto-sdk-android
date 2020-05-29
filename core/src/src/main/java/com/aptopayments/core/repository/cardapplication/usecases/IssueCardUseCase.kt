package com.aptopayments.core.repository.cardapplication.usecases

import com.aptopayments.core.data.card.Card
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.cardapplication.CardApplicationRepository

internal class IssueCardUseCase constructor(
    private val applicationRepository: CardApplicationRepository,
    networkHandler: NetworkHandler
) : UseCase<Card, IssueCardUseCase.Params>(networkHandler) {

    data class Params(val applicationId: String, val additionalFields: Map<String, Any>?)

    override fun run(params: Params) = applicationRepository.issueCard(params.applicationId, params.additionalFields)
}
