package com.aptopayments.core.repository.card

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.card.ActivatePhysicalCardResult
import com.aptopayments.core.data.card.Card
import com.aptopayments.core.data.card.CardDetails
import com.aptopayments.core.data.card.ProvisioningData
import com.aptopayments.core.data.fundingsources.Balance
import com.aptopayments.core.data.oauth.OAuthCredential
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.platform.BaseRepository
import com.aptopayments.core.repository.UserSessionRepository
import com.aptopayments.core.repository.card.local.CardBalanceLocalDao
import com.aptopayments.core.repository.card.local.CardLocalRepository
import com.aptopayments.core.repository.card.local.entities.CardBalanceLocalEntity
import com.aptopayments.core.repository.card.remote.CardService
import com.aptopayments.core.repository.card.remote.entities.ActivatePhysicalCardEntity
import com.aptopayments.core.repository.card.remote.entities.CardEntity
import com.aptopayments.core.repository.card.remote.requests.AddCardBalanceRequest
import com.aptopayments.core.repository.card.remote.requests.GetCardRequest
import com.aptopayments.core.repository.card.remote.requests.IssueCardRequest
import com.aptopayments.core.repository.card.remote.requests.OAuthCredentialRequest
import com.aptopayments.core.repository.card.usecases.*
import com.aptopayments.core.repository.fundingsources.remote.entities.BalanceEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal interface CardRepository : BaseRepository {
    fun issueCard(cardProductId: String, credential: OAuthCredential?,
                  additionalFields: Map<String, Any>?, initialFundingSourceId: String?): Either<Failure, Card>
    fun getCard(params: GetCardParams): Either<Failure, Card>
    fun getCardDetails(cardId: String): Either<Failure, CardDetails>
    fun getCards(): Either<Failure, List<Card>>
    fun unlockCard(cardId: String): Either<Failure, Card>
    fun lockCard(cardId: String): Either<Failure, Card>
    fun activatePhysicalCard(cardId: String, code: String): Either<Failure, ActivatePhysicalCardResult>
    fun getCardBalance(params: GetCardBalanceParams): Either<Failure, Balance>
    fun setCardBalance(params: SetCardBalanceParams): Either<Failure, Balance>
    fun addCardBalance(params: AddCardBalanceParams): Either<Failure, Balance>
    fun setPin(params: SetPinParams): Either<Failure, Card>
    fun getProvisioningData(cardId: String, clientAppId: String, clientDeviceId:String, walletId: String): Either<Failure, ProvisioningData>

    class Network constructor(
            private val networkHandler: NetworkHandler,
            private val service: CardService,
            private val cardLocalRepo: CardLocalRepository,
            private val cardBalanceLocalDao: CardBalanceLocalDao,
            userSessionRepository: UserSessionRepository
    ) : BaseRepository.BaseRepositoryImpl(), CardRepository {

        init {
            userSessionRepository.subscribeSessionInvalidListener(this) {
                GlobalScope.launch {
                    cardLocalRepo.clearCardCache()
                    cardBalanceLocalDao.clearCardBalanceCache()
                }
            }
        }

        protected fun finalize() {
            userSessionRepository.unsubscribeSessionInvalidListener(this)
        }

        override fun issueCard(cardProductId: String, credential: OAuthCredential?,
                               additionalFields: Map<String, Any>?, initialFundingSourceId: String?) =
                when (networkHandler.isConnected) {
                    true -> {
                        val credentialRequest: OAuthCredentialRequest? = credential?.let {
                            OAuthCredentialRequest(
                                    accessToken = it.oauthToken,
                                    refreshToken = it.refreshToken
                            )
                        }
                        val issueCardRequest = IssueCardRequest(
                                cardProductId = cardProductId,
                                oAuthCredentialRequest = credentialRequest,
                                additionalFields = additionalFields,
                                initialFundingSourceId = initialFundingSourceId
                        )
                        request(service.issueCard(issueCardRequest), { it.toCard() }, CardEntity())
                    }
                    false, null -> Either.Left(Failure.NetworkConnection)
                }

        override fun getCard(params: GetCardParams): Either<Failure, Card> {
            return if (params.refresh) {
                getCardFromRemoteAPI(params.cardId, params.showDetails)
            }
            else {
                cardLocalRepo.getCard(params.cardId)?.let { Either.Right(it) }
                    ?: getCardFromRemoteAPI(params.cardId, params.showDetails)
            }
        }

        override fun getCardDetails(cardId: String): Either<Failure, CardDetails> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.getCardDetails(cardId)) { it.toCardDetails() }
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getCards(): Either<Failure, List<Card>> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.getCards(), { listEntity: ListEntity<CardEntity> ->
                        listEntity.data?.map {
                            it.toCard()
                        } ?: emptyList()
                    }, ListEntity())
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun unlockCard(cardId: String): Either<Failure, Card> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.unlockCard(cardId), { it.toCard() }, CardEntity())
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun lockCard(cardId: String): Either<Failure, Card> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.lockCard(cardId), { it.toCard() }, CardEntity())
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun activatePhysicalCard(cardId: String, code: String): Either<Failure, ActivatePhysicalCardResult> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.activatePhysicalCard(cardId, code), { it.toActivatePhysicalCardResult() }, ActivatePhysicalCardEntity())
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getCardBalance(params: GetCardBalanceParams): Either<Failure, Balance> {
            return if (params.refresh) {
                getCardBalanceFromRemoteAPI(params.cardID)
            } else {
                cardBalanceLocalDao.getCardBalance(params.cardID)?.let { localCardBalance ->
                    return Either.Right(localCardBalance.toBalance())
                } ?: return getCardBalanceFromRemoteAPI(params.cardID)
            }
        }

        override fun setCardBalance(params: SetCardBalanceParams): Either<Failure, Balance> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.setCardBalance(params.cardID, params.fundingSourceID), { it.toBalance() }, BalanceEntity())
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun addCardBalance(params: AddCardBalanceParams): Either<Failure, Balance> {
            return when (networkHandler.isConnected) {
                true -> {
                    val addCardBalanceRequest = AddCardBalanceRequest(params.fundingSourceType, params.tokenId)
                    request(service.addCardBalance(params.cardID, addCardBalanceRequest), { it.toBalance() }, BalanceEntity())
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun setPin(params: SetPinParams): Either<Failure, Card> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.setPin(params.cardId, params.pin), { it.toCard() }, CardEntity())
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun getCardFromRemoteAPI(cardId: String, showDetails: Boolean): Either<Failure, Card> {
            return when (networkHandler.isConnected) {
                true -> {
                    val getCardRequest = GetCardRequest(accountID = cardId, showDetails = showDetails)
                    request(service.getCard(getCardRequest), {
                        val card = it.toCard()
                        cardLocalRepo.saveCard(card)
                        card
                    }, CardEntity())
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        private fun getCardBalanceFromRemoteAPI(cardID: String): Either<Failure, Balance> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.getCardBalance(cardID), {
                        val balance = it.toBalance()
                        cardBalanceLocalDao.clearCardBalanceCache()
                        cardBalanceLocalDao.saveCardBalance(CardBalanceLocalEntity.fromBalance(cardID, balance))
                        balance
                    }, BalanceEntity())
                }
                false, null -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getProvisioningData(
            cardId: String,
            clientAppId: String,
            clientDeviceId: String,
            walletId: String
        ): Either<Failure, ProvisioningData> {

            return when (networkHandler.isConnected) {
                true -> {
                    request(
                        service.getProvisioningData(
                            cardId,
                            clientAppId,
                            clientDeviceId,
                            walletId
                        )
                    ) { it.pushTokenizeRequestData.toProvisioningData() }
                }
                else -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}
