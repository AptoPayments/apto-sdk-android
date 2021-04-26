package com.aptopayments.mobile.repository.cardapplication.usecases

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.IssueCardDesign
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.cardapplication.CardApplicationRepository

internal class IssueCardUseCase(
    private val applicationRepository: CardApplicationRepository,
    networkHandler: NetworkHandler
) : UseCase<Card, IssueCardUseCase.Params>(networkHandler) {

    override fun run(params: Params) =
        applicationRepository.issueCard(params.applicationId, params.additionalFields, params.metadata, params.design)

    data class Params(
        val applicationId: String,
        val additionalFields: Map<String, Any>?,
        val metadata: String?,
        val design: IssueCardDesign?
    )
}
