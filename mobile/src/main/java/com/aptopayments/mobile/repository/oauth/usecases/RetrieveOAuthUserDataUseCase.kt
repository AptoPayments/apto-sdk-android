package com.aptopayments.mobile.repository.oauth.usecases

import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdate
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.oauth.OAuthRepository

internal class RetrieveOAuthUserDataUseCase constructor(
    private val repository: OAuthRepository,
    networkHandler: NetworkHandler
) : UseCase<OAuthUserDataUpdate, RetrieveOAuthUserDataUseCase.Params>(networkHandler) {

    data class Params(
        val allowedBalanceType: AllowedBalanceType,
        val tokenId: String
    )

    override fun run(params: Params) = repository.retrieveOAuthUserData(
        allowedBalanceType = params.allowedBalanceType,
        tokenId = params.tokenId
    )
}
