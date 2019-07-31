package com.aptopayments.core.repository.card.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.card.remote.requests.IssueCardRequest
import com.aptopayments.core.repository.card.remote.requests.*
import javax.inject.Inject

internal class CardService
@Inject
constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val cardApi by lazy { apiCatalog.api().create(CardApi::class.java) }
    private val vaultCardApi by lazy { apiCatalog.vaultApi().create(CardApi::class.java) }

    fun issueCard(issueCardRequest: IssueCardRequest) = cardApi.issueCard(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            request = issueCardRequest
    )

    fun getCard(getCardRequest: GetCardRequest) = cardApi.getCard(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = getCardRequest.accountID,
            showDetails = getCardRequest.showDetails
    )

    fun getCardDetails(cardId: String) = vaultCardApi.getCardDetails(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardId,
            showDetails = true
    )

    fun getCards() = cardApi.getCards(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken)
    )

    fun unlockCard(cardId: String) = cardApi.changeCardState(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardId,
            action = "enable"
    )

    fun lockCard(cardId: String) = cardApi.changeCardState(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardId,
            action = "disable"
    )

    fun activatePhysicalCard(cardId: String, code: String) = cardApi.activatePhysicalCard(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardId,
            request = ActivatePhysicalCardRequest(code)
    )

    fun getCardBalance(cardID: String) = cardApi.getCardBalance(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardID
    )

    fun setCardBalance(cardID: String, fundingSourceID: String) = cardApi.setCardFundingSource(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardID,
            request = SetCardFundingSourceRequest(fundingSourceID)
    )

    fun addCardBalance(cardID: String, request: AddCardBalanceRequest) = cardApi.addCardBalance(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardID,
            request = request
    )

    fun setPin(cardId: String, pin: String) = cardApi.setPin(
            apiKey = ApiCatalog.apiKey,
            userToken = authorizationHeader(userSessionRepository.userToken),
            accountID = cardId,
            request = SetPinRequest(pin)
    )
}
