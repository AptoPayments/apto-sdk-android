package com.aptopayments.core.repository.cardapplication.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.cardapplication.remote.entities.AcceptDisclaimerRequest
import com.aptopayments.core.repository.cardapplication.remote.entities.IssueCardRequest
import com.aptopayments.core.repository.cardapplication.remote.entities.NewCardApplicationRequest
import com.aptopayments.core.repository.cardapplication.remote.entities.SelectBalanceStoreRequest

internal class CardApplicationService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val cardApplicationApi by lazy { apiCatalog.api().create(CardApplicationApi::class.java) }

    fun startCardApplication(cardProductId: String) =
        cardApplicationApi.startCardApplication(applicationRequest = NewCardApplicationRequest(cardProductId = cardProductId))

    fun getCardApplication(cardApplicationId: String) =
        cardApplicationApi.getCardApplication(cardApplicationId = cardApplicationId)

    internal fun cancelCardApplication(cardApplicationId: String) =
        cardApplicationApi.cancelCardApplication(cardApplicationId = cardApplicationId)

    fun setBalanceStore(cardApplicationId: String, tokenId: String) =
        cardApplicationApi.setBalanceStore(
            cardApplicationId = cardApplicationId,
            request = SelectBalanceStoreRequest(tokenId)
        )

    fun acceptDisclaimer(workflowObjectId: String, actionId: String) =
        cardApplicationApi.acceptDisclaimer(
            request = AcceptDisclaimerRequest(
                workflowObjectId = workflowObjectId,
                actionId = actionId
            )
        )

    fun issueCard(applicationId: String, additionalFields: Map<String, Any>?) =
        cardApplicationApi.issueCard(request = IssueCardRequest(applicationId, additionalFields))
}
