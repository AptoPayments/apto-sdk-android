package com.aptopayments.mobile.repository.cardapplication

import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.CardApplication
import com.aptopayments.mobile.data.card.SelectBalanceStoreResult
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.cardapplication.remote.CardApplicationService

internal interface CardApplicationRepository : BaseNoNetworkRepository {
    fun startCardApplication(cardProductId: String): Either<Failure, CardApplication>
    fun getCardApplication(cardApplicationId: String): Either<Failure, CardApplication>
    fun cancelCardApplication(cardApplicationId: String): Either<Failure, Unit>
    fun setBalanceStore(cardApplicationId: String, tokenId: String): Either<Failure, SelectBalanceStoreResult>
    fun acceptDisclaimer(workflowObjectId: String, actionId: String): Either<Failure, Unit>
    fun issueCard(
        applicationId: String,
        additionalFields: Map<String, Any>? = null,
        metadata: String? = null
    ): Either<Failure, Card>
}

internal class CardApplicationRepositoryImpl(
    private val cardApplicationService: CardApplicationService
) : CardApplicationRepository {

    override fun startCardApplication(cardProductId: String) =
        cardApplicationService.startCardApplication(cardProductId)

    override fun getCardApplication(cardApplicationId: String) =
        cardApplicationService.getCardApplication(cardApplicationId)

    override fun cancelCardApplication(cardApplicationId: String) =
        cardApplicationService.cancelCardApplication(cardApplicationId)

    override fun setBalanceStore(
        cardApplicationId: String,
        tokenId: String
    ) = cardApplicationService.setBalanceStore(
        cardApplicationId = cardApplicationId,
        tokenId = tokenId
    )

    override fun acceptDisclaimer(
        workflowObjectId: String,
        actionId: String
    ) = cardApplicationService.acceptDisclaimer(workflowObjectId = workflowObjectId, actionId = actionId)

    override fun issueCard(
        applicationId: String,
        additionalFields: Map<String, Any>?,
        metadata: String?
    ) = cardApplicationService.issueCard(applicationId, additionalFields, metadata)
}
