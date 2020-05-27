package com.aptopayments.core.repository.card.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.card.remote.requests.*

internal class CardService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val cardApi by lazy { apiCatalog.api().create(CardApi::class.java) }
    private val vaultCardApi by lazy { apiCatalog.vaultApi().create(CardApi::class.java) }

    fun issueCard(issueCardRequest: IssueCardRequest) = cardApi.issueCard(request = issueCardRequest)

    fun getCard(getCardRequest: GetCardRequest) =
        cardApi.getCard(accountID = getCardRequest.accountID, showDetails = false)

    fun getCardDetails(cardId: String) = vaultCardApi.getCardDetails(accountID = cardId, showDetails = true)

    fun getCards() = cardApi.getCards()

    fun unlockCard(cardId: String) = cardApi.changeCardState(accountID = cardId, action = "enable")

    fun lockCard(cardId: String) = cardApi.changeCardState(accountID = cardId, action = "disable")

    fun activatePhysicalCard(cardId: String, code: String) =
        cardApi.activatePhysicalCard(accountID = cardId, request = ActivatePhysicalCardRequest(code))

    fun getCardBalance(cardID: String) = cardApi.getCardBalance(accountID = cardID)

    fun setCardBalance(cardID: String, fundingSourceID: String) =
        cardApi.setCardFundingSource(accountID = cardID, request = SetCardFundingSourceRequest(fundingSourceID))

    fun addCardBalance(cardID: String, request: AddCardBalanceRequest) =
        cardApi.addCardBalance(accountID = cardID, request = request)

    fun setPin(cardId: String, pin: String) = cardApi.setPin(accountID = cardId, request = SetPinRequest(pin))

    fun getProvisioningData(cardId: String, clientAppId: String, clientDeviceId: String, walletId: String) =
        cardApi.getProvisioningOPC(
            accountID = cardId,
            request = GetProvisioningDataRequestWrapper(
                GetProvisioningDataRequest(
                    clientAppId,
                    clientDeviceId,
                    walletId
                )
            )
        )
}
