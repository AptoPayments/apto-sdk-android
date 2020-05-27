package com.aptopayments.core.repository.oauth.usecases

import com.aptopayments.core.data.oauth.OAuthUserDataUpdate
import com.aptopayments.core.data.workflowaction.AllowedBalanceType
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.oauth.OAuthRepository

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
