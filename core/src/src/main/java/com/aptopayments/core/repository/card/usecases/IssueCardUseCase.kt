package com.aptopayments.core.repository.card.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.card.Card
import com.aptopayments.core.data.oauth.OAuthCredential
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.card.CardRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class IssueCardUseCase @Inject constructor(
        private val cardRepository: CardRepository,
        networkHandler: NetworkHandler
) : UseCase<Card, IssueCardUseCase.Params>(networkHandler) {
    data class Params(
            val cardProductId: String,
            val credential: OAuthCredential?,
            val useBalanceV2: Boolean
    )

    override fun run(params: Params) = cardRepository.issueCard(
            cardProductId = params.cardProductId,
            credential = params.credential,
            useBalanceV2 = params.useBalanceV2
    )
}
