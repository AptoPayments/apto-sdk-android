package com.aptopayments.core.repository.cardapplication

import com.aptopayments.core.data.card.Card
import com.aptopayments.core.data.card.CardApplication
import com.aptopayments.core.data.card.IssueCardAdditionalFields
import com.aptopayments.core.data.card.SelectBalanceStoreResult
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.platform.BaseRepository
import com.aptopayments.core.repository.card.remote.entities.CardEntity
import com.aptopayments.core.repository.cardapplication.remote.CardApplicationService
import com.aptopayments.core.repository.cardapplication.remote.entities.CardApplicationEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.SelectBalanceStoreResultEntity

internal interface CardApplicationRepository : BaseRepository {

    fun startCardApplication(cardProductId: String): Either<Failure, CardApplication>
    fun getCardApplication(cardApplicationId: String): Either<Failure, CardApplication>
    fun cancelCardApplication(cardApplicationId: String): Either<Failure, Unit>
    fun setBalanceStore(cardApplicationId: String, tokenId: String): Either<Failure, SelectBalanceStoreResult>
    fun acceptDisclaimer(workflowObjectId: String, actionId: String): Either<Failure, Unit>
    fun issueCard(applicationId: String, additionalFields: IssueCardAdditionalFields?): Either<Failure, Card>

    class Network constructor(
        private val networkHandler: NetworkHandler,
        private val cardApplicationService: CardApplicationService
    ) : BaseRepository.BaseRepositoryImpl(), CardApplicationRepository {

        override fun startCardApplication(cardProductId: String): Either<Failure, CardApplication> {
            if (!networkHandler.isConnected) {
                return Either.Left(Failure.NetworkConnection)
            }
            return request(
                cardApplicationService.startCardApplication(cardProductId),
                { it.toCardApplication() },
                CardApplicationEntity()
            )
        }

        override fun getCardApplication(cardApplicationId: String): Either<Failure, CardApplication> {
            if (!networkHandler.isConnected) {
                return Either.Left(Failure.NetworkConnection)
            }
            return request(
                cardApplicationService.getCardApplication(cardApplicationId),
                { it.toCardApplication() },
                CardApplicationEntity()
            )
        }

        override fun cancelCardApplication(cardApplicationId: String): Either<Failure, Unit> {
            if (!networkHandler.isConnected) {
                return Either.Left(Failure.NetworkConnection)
            }
            return request(cardApplicationService.cancelCardApplication(cardApplicationId), {}, Unit)
        }

        override fun setBalanceStore(
            cardApplicationId: String,
            tokenId: String
        ): Either<Failure, SelectBalanceStoreResult> {
            if (!networkHandler.isConnected) {
                return Either.Left(Failure.NetworkConnection)
            }
            return request(
                cardApplicationService.setBalanceStore(
                    cardApplicationId = cardApplicationId,
                    tokenId = tokenId
                ),
                { it.toSelectBalanceStoreResult() },
                SelectBalanceStoreResultEntity()
            )
        }

        override fun acceptDisclaimer(
            workflowObjectId: String,
            actionId: String
        ): Either<Failure, Unit> {
            if (!networkHandler.isConnected) {
                return Either.Left(Failure.NetworkConnection)
            }
            return request(
                cardApplicationService.acceptDisclaimer(
                    workflowObjectId = workflowObjectId, actionId = actionId
                ), {}, Unit
            )
        }

        override fun issueCard(
            applicationId: String,
            additionalFields: IssueCardAdditionalFields?
        ): Either<Failure, Card> {
            return if (!networkHandler.isConnected) {
                Either.Left(Failure.NetworkConnection)
            } else {
                request(
                    cardApplicationService.issueCard(applicationId, additionalFields?.fields),
                    { it.toCard() },
                    CardEntity()
                )
            }
        }
    }
}
