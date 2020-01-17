package com.aptopayments.core.repository.card.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.card.Card
import com.aptopayments.core.data.oauth.OAuthCredential
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class IssueCardUseCase constructor(
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
