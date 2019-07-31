package com.aptopayments.core.repository.oauth.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.oauth.OAuthAttempt
import com.aptopayments.core.data.workflowaction.AllowedBalanceType
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.oauth.OAuthRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class StartOAuthAuthenticationUseCase @Inject constructor(
        private val repository: OAuthRepository,
        networkHandler: NetworkHandler
) : UseCase<OAuthAttempt, AllowedBalanceType>(networkHandler) {

    override fun run(params: AllowedBalanceType) = repository.startOAuthAuthentication(params)
}
