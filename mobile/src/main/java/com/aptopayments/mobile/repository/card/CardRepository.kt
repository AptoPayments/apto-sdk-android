package com.aptopayments.mobile.repository.card

import com.aptopayments.mobile.data.ListPagination
import com.aptopayments.mobile.data.PaginatedList
import com.aptopayments.mobile.data.card.*
import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.card.local.CardBalanceLocalDao
import com.aptopayments.mobile.repository.card.local.CardLocalRepository
import com.aptopayments.mobile.repository.card.local.entities.CardBalanceLocalEntity
import com.aptopayments.mobile.repository.card.remote.CardService
import com.aptopayments.mobile.repository.card.remote.requests.GetCardRequest
import com.aptopayments.mobile.repository.card.usecases.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

internal interface CardRepository : BaseNoNetworkRepository {
    fun getCard(params: GetCardParams): Either<Failure, Card>
    fun getCards(pagination: ListPagination? = null): Either<Failure, PaginatedList<Card>>
    fun unlockCard(cardId: String): Either<Failure, Card>
    fun lockCard(cardId: String): Either<Failure, Card>
    fun activatePhysicalCard(cardId: String, code: String): Either<Failure, ActivatePhysicalCardResult>
    fun getCardBalance(params: GetCardBalanceParams): Either<Failure, Balance>
    fun setCardBalance(params: SetCardBalanceParams): Either<Failure, Balance>
    fun addCardBalance(params: AddCardBalanceParams): Either<Failure, Balance>
    fun setPin(params: SetPinParams): Either<Failure, Card>
    fun getProvisioningData(
        cardId: String
    ): Either<Failure, ProvisioningData>

    fun setCardPasscode(cardId: String, passcode: String, verificationId: String?): Either<Failure, Unit>
    fun orderPhysicalCard(cardId: String): Either<Failure, Card>
    fun getOrderPhysicalCardConfig(cardId: String): Either<Failure, OrderPhysicalCardConfig>
}

internal class CardRepositoryImpl(
    private val service: CardService,
    private val cardLocalRepo: CardLocalRepository,
    private val cardBalanceLocalDao: CardBalanceLocalDao,
    private val userSessionRepository: UserSessionRepository
) : CardRepository {

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

    override fun getCard(params: GetCardParams): Either<Failure, Card> {
        return if (params.refresh) {
            getCardFromRemoteAPI(params.cardId)
        } else {
            cardLocalRepo.getCard(params.cardId)?.right() ?: getCardFromRemoteAPI(params.cardId)
        }
    }

    override fun getCards(pagination: ListPagination?): Either<Failure, PaginatedList<Card>> {
        return service.getCards(pagination?.startingAfter, pagination?.endingBefore, pagination?.limit)
    }

    override fun unlockCard(cardId: String) = service.unlockCard(cardId)

    override fun lockCard(cardId: String) = service.lockCard(cardId)

    override fun activatePhysicalCard(cardId: String, code: String) = service.activatePhysicalCard(cardId, code)

    override fun getCardBalance(params: GetCardBalanceParams): Either<Failure, Balance> {
        return if (params.refresh) {
            getCardBalanceFromRemoteAPI(params.cardID)
        } else {
            cardBalanceLocalDao.getCardBalance(params.cardID)?.toBalance()?.right()
                ?: getCardBalanceFromRemoteAPI(params.cardID)
        }
    }

    override fun setCardBalance(params: SetCardBalanceParams) =
        service.setCardBalance(params.cardID, params.fundingSourceID)

    override fun addCardBalance(params: AddCardBalanceParams) =
        service.addCardBalance(params.cardID, params.fundingSourceType, params.tokenId)

    override fun setPin(params: SetPinParams) = service.setPin(params.cardId, params.pin)

    private fun getCardFromRemoteAPI(cardId: String): Either<Failure, Card> {
        val getCardRequest = GetCardRequest(accountID = cardId)

        return service.getCard(getCardRequest).runIfRight { cardLocalRepo.saveCard(it) }
    }

    private fun getCardBalanceFromRemoteAPI(cardID: String): Either<Failure, Balance> {
        return service.getCardBalance(cardID).runIfRight {
            cardBalanceLocalDao.clearCardBalanceCache()
            cardBalanceLocalDao.saveCardBalance(CardBalanceLocalEntity.fromBalance(cardID, it))
        }
    }

    override fun getProvisioningData(cardId: String) = service.getProvisioningData(cardId)

    override fun setCardPasscode(cardId: String, passcode: String, verificationId: String?) =
        service.setCardPasscode(cardId, passcode, verificationId)

    override fun getOrderPhysicalCardConfig(cardId: String) = service.getOrderPhysicalCardConfig(cardId)

    override fun orderPhysicalCard(cardId: String) = service.orderPhysicalCard(cardId)
}
