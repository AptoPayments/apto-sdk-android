package com.aptopayments.mobile.repository.cardapplication.remote

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.IssueCardDesign
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity
import com.aptopayments.mobile.repository.cardapplication.remote.entities.*

internal class CardApplicationService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val cardApplicationApi by lazy { apiCatalog.api().create(CardApplicationApi::class.java) }

    fun startCardApplication(cardProductId: String) =
        request(
            call = cardApplicationApi.startCardApplication(
                applicationRequest = NewCardApplicationRequest(
                    cardProductId = cardProductId
                )
            ),
            transform = { it.toCardApplication() },
            CardApplicationEntity()
        )

    fun getCardApplication(cardApplicationId: String) =
        request(
            call = cardApplicationApi.getCardApplication(cardApplicationId = cardApplicationId),
            transform = { it.toCardApplication() },
            default = CardApplicationEntity()
        )

    internal fun cancelCardApplication(cardApplicationId: String) =
        request(
            call = cardApplicationApi.cancelCardApplication(cardApplicationId = cardApplicationId),
            transform = {},
            default = Unit
        )

    fun setBalanceStore(cardApplicationId: String, tokenId: String) =
        request(
            call = cardApplicationApi.setBalanceStore(
                cardApplicationId = cardApplicationId,
                request = SelectBalanceStoreRequest(tokenId)
            ),
            transform = { it.toSelectBalanceStoreResult() },
            default = SelectBalanceStoreResultEntity()
        )

    fun acceptDisclaimer(workflowObjectId: String, actionId: String) =
        request(
            cardApplicationApi.acceptDisclaimer(
                request = AcceptDisclaimerRequest(
                    workflowObjectId = workflowObjectId,
                    actionId = actionId
                )
            ),
            transform = {},
            default = Unit
        )

    fun issueCard(
        applicationId: String,
        metadata: String?,
        design: IssueCardDesign?
    ): Either<Failure, Card> {
        val designRequest = design?.let { IssueCardDesignRequest.from(design) }
        val request = IssueCardRequest(applicationId, metadata, designRequest)
        return request(
            call = cardApplicationApi.issueCard(request = request),
            transform = { it.toCard() },
            default = CardEntity()
        )
    }
}
