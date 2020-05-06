package com.aptopayments.core.repository.oauth.usecases

import com.aptopayments.core.data.oauth.OAuthAttempt
import com.aptopayments.core.data.workflowaction.AllowedBalanceType
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.oauth.OAuthRepository

internal class StartOAuthAuthenticationUseCase constructor(
        private val repository: OAuthRepository,
        networkHandler: NetworkHandler
) : UseCase<OAuthAttempt, AllowedBalanceType>(networkHandler) {

    override fun run(params: AllowedBalanceType) = repository.startOAuthAuthentication(params)
}
