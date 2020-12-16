package com.aptopayments.mobile.repository.oauth.usecases

import com.aptopayments.mobile.data.oauth.OAuthAttempt
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.oauth.OAuthRepository

internal class GetOAuthAttemptStatusUseCase(
    private val repository: OAuthRepository,
    networkHandler: NetworkHandler
) : UseCase<OAuthAttempt, String>(networkHandler) {

    override fun run(params: String) = repository.getOAuthAttemptStatus(attemptId = params)
}
