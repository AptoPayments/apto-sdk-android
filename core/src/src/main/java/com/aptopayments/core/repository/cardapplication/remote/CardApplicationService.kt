package com.aptopayments.core.repository.cardapplication.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.cardapplication.remote.entities.AcceptDisclaimerRequest
import com.aptopayments.core.repository.cardapplication.remote.entities.IssueCardRequest
import com.aptopayments.core.repository.cardapplication.remote.entities.NewCardApplicationRequest
import com.aptopayments.core.repository.cardapplication.remote.entities.SelectBalanceStoreRequest
import javax.inject.Inject

internal class CardApplicationService
@Inject
constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val cardApplicationApi by lazy { apiCatalog.api().create(CardApplicationApi::class.java) }

    fun startCardApplication(cardProductId: String) =
            cardApplicationApi.startCardApplication(
                    ApiCatalog.apiKey,
                    userToken = authorizationHeader(userSessionRepository.userToken),
                    applicationRequest = NewCardApplicationRequest(cardProductId = cardProductId)
            )

    fun getCardApplication(cardApplicationId: String) =
            cardApplicationApi.getCardApplication(
                    ApiCatalog.apiKey,
                    userToken = authorizationHeader(userSessionRepository.userToken),
                    cardApplicationId = cardApplicationId
            )

    internal  fun cancelCardApplication(cardApplicationId: String) =
            cardApplicationApi.cancelCardApplication(
                    ApiCatalog.apiKey,
                    userToken = authorizationHeader(userSessionRepository.userToken),
                    cardApplicationId = cardApplicationId
            )

    fun setBalanceStore(cardApplicationId: String, tokenId: String) =
            cardApplicationApi.setBalanceStore(
                    ApiCatalog.apiKey,
                    userToken = authorizationHeader(userSessionRepository.userToken),
                    cardApplicationId = cardApplicationId,
                    request = SelectBalanceStoreRequest(tokenId)
            )

    fun acceptDisclaimer(workflowObjectId: String, actionId: String) =
            cardApplicationApi.acceptDisclaimer(
                    ApiCatalog.apiKey,
                    userToken = authorizationHeader(userSessionRepository.userToken),
                    request = AcceptDisclaimerRequest(
                            workflowObjectId = workflowObjectId,
                            actionId = actionId
                    )
            )

    fun issueCard(applicationId: String, balanceVersion: String) =
            cardApplicationApi.issueCard(
                    ApiCatalog.apiKey,
                    userToken = authorizationHeader(userSessionRepository.userToken),
                    request = IssueCardRequest(
                            applicationId = applicationId,
                            balanceVersion = balanceVersion
                    )
            )

}
