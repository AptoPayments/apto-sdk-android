package com.aptopayments.core.repository.card.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.card.remote.requests.*

internal class CardService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val cardApi by lazy { apiCatalog.api().create(CardApi::class.java) }
    private val vaultCardApi by lazy { apiCatalog.vaultApi().create(CardApi::class.java) }

    fun issueCard(issueCardRequest: IssueCardRequest) = cardApi.issueCard(
            userToken = authorizationHeader(userSessionRepository.userToken),
            request = issueCardRequest
    )

    fun getCard(getCardRequest: GetCardRequest) = cardApi.getCard(
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = getCardRequest.accountID,
            showDetails = getCardRequest.showDetails
    )

    fun getCardDetails(cardId: String) = vaultCardApi.getCardDetails(
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardId,
            showDetails = true
    )

    fun getCards() = cardApi.getCards(
            userToken = authorizationHeader(userSessionRepository.userToken)
    )

    fun unlockCard(cardId: String) = cardApi.changeCardState(
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardId,
            action = "enable"
    )

    fun lockCard(cardId: String) = cardApi.changeCardState(
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardId,
            action = "disable"
    )

    fun activatePhysicalCard(cardId: String, code: String) = cardApi.activatePhysicalCard(
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardId,
            request = ActivatePhysicalCardRequest(code)
    )

    fun getCardBalance(cardID: String) = cardApi.getCardBalance(
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardID
    )

    fun setCardBalance(cardID: String, fundingSourceID: String) = cardApi.setCardFundingSource(
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardID,
            request = SetCardFundingSourceRequest(fundingSourceID)
    )

    fun addCardBalance(cardID: String, request: AddCardBalanceRequest) = cardApi.addCardBalance(
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardID,
            request = request
    )

    fun setPin(cardId: String, pin: String) = cardApi.setPin(
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardId,
            request = SetPinRequest(pin)
    )
}
