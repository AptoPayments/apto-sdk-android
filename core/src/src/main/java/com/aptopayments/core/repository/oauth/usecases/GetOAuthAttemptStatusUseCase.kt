package com.aptopayments.core.repository.oauth.usecases

import com.aptopayments.core.data.oauth.OAuthAttempt
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.oauth.OAuthRepository

internal class GetOAuthAttemptStatusUseCase constructor(
    private val repository: OAuthRepository,
    networkHandler: NetworkHandler
) : UseCase<OAuthAttempt, String>(networkHandler) {

    override fun run(params: String) = repository.getOAuthAttemptStatus(attemptId = params)
}
