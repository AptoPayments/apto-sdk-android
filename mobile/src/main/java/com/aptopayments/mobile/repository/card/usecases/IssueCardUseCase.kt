package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.oauth.OAuthCredential
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository

internal class IssueCardUseCase(
    private val cardRepository: CardRepository,
    networkHandler: NetworkHandler
) : UseCase<Card, IssueCardUseCase.Params>(networkHandler) {
    data class Params(
        val cardProductId: String,
        val credential: OAuthCredential?,
        val additionalFields: Map<String, Any>?,
        val initialFundingSourceId: String?
    )

    override fun run(params: Params) = cardRepository.issueCard(
        cardProductId = params.cardProductId,
        credential = params.credential,
        additionalFields = params.additionalFields,
        initialFundingSourceId = params.initialFundingSourceId
    )
}
