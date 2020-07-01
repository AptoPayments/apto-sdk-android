package com.aptopayments.mobile.repository.oauth.usecases

import com.aptopayments.mobile.data.oauth.OAuthAttempt
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.oauth.OAuthRepository

internal class StartOAuthAuthenticationUseCase constructor(
    private val repository: OAuthRepository,
    networkHandler: NetworkHandler
) : UseCase<OAuthAttempt, AllowedBalanceType>(networkHandler) {

    override fun run(params: AllowedBalanceType) = repository.startOAuthAuthentication(params)
}
