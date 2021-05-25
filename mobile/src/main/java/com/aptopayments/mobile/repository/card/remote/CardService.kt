package com.aptopayments.mobile.repository.card.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.network.PaginatedListEntity
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.card.remote.entities.ActivatePhysicalCardEntity
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity
import com.aptopayments.mobile.repository.card.remote.requests.*
import com.aptopayments.mobile.repository.fundingsources.remote.entities.BalanceEntity

private const val STATE_ENABLE = "enable"
private const val STATE_DISABLE = "disable"

internal class CardService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val cardApi by lazy { apiCatalog.api().create(CardApi::class.java) }

    fun issueCard(issueCardRequest: IssueCardRequest) =
        request(cardApi.issueCard(request = issueCardRequest), { it.toCard() }, CardEntity())

    fun getCard(getCardRequest: GetCardRequest) =
        request(
            cardApi.getCard(accountID = getCardRequest.accountID, showDetails = false),
            { it.toCard() },
            CardEntity()
        )

    fun getCards(startingAfter: String? = null, endingBefore: String? = null, limit: Int? = null) =
        request(
            cardApi.getCards(startingAfter, endingBefore, limit),
            { listEntity: PaginatedListEntity<CardEntity> ->
                listEntity.toPaginatedList { it.toCard() }
            },
            PaginatedListEntity()
        )

    fun unlockCard(cardId: String) =
        request(
            cardApi.changeCardState(accountID = cardId, action = STATE_ENABLE),
            { it.toCard() },
            CardEntity()
        )

    fun lockCard(cardId: String) =
        request(
            cardApi.changeCardState(accountID = cardId, action = STATE_DISABLE),
            { it.toCard() },
            CardEntity()
        )

    fun activatePhysicalCard(cardId: String, code: String) =
        request(
            cardApi.activatePhysicalCard(accountID = cardId, request = ActivatePhysicalCardRequest(code)),
            { it.toActivatePhysicalCardResult() },
            ActivatePhysicalCardEntity()
        )

    fun getCardBalance(cardID: String) =
        request(
            cardApi.getCardBalance(accountID = cardID),
            { it.toBalance() },
            BalanceEntity()
        )

    fun setCardBalance(cardID: String, fundingSourceID: String) =
        request(
            cardApi.setCardFundingSource(accountID = cardID, request = SetCardFundingSourceRequest(fundingSourceID)),
            { it.toBalance() },
            BalanceEntity()
        )

    fun addCardBalance(cardID: String, fundingSourceType: String, tokenId: String) =
        request(
            cardApi.addCardBalance(accountID = cardID, request = AddCardBalanceRequest(fundingSourceType, tokenId)),
            { it.toBalance() },
            BalanceEntity()
        )

    fun setPin(cardId: String, pin: String) =
        request(cardApi.setPin(accountID = cardId, request = SetPinRequest(pin)), { it.toCard() }, CardEntity())

    fun getProvisioningData(cardId: String, clientAppId: String, clientDeviceId: String, walletId: String) =
        request(
            cardApi.getProvisioningOPC(
                accountID = cardId,
                request = GetProvisioningDataRequestWrapper(
                    GetProvisioningDataRequest(
                        clientAppId,
                        clientDeviceId,
                        walletId
                    )
                )
            ),
            { it.pushTokenizeRequestData.toProvisioningData() }
        )

    fun setCardPasscode(cardId: String, passcode: String, verificationId: String?) =
        request(
            cardApi.setCardPasscode(
                cardId = cardId,
                request = SetPasscodeRequest(passcode = passcode, verificationId = verificationId)
            ),
            { }
        )

    fun getOrderPhysicalCardConfig(cardId: String) =
        request(
            cardApi.getOrderPhysicalCardConfig(cardId = cardId),
            { it.toOrderPhysicalCardConfig() }
        )

    fun orderPhysicalCard(cardId: String) =
        request(
            cardApi.orderPhysicalCard(cardId = cardId),
            { it.toCard() }
        )
}
