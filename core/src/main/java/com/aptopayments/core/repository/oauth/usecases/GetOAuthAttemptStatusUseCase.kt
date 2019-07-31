package com.aptopayments.core.repository.oauth.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.oauth.OAuthAttempt
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.oauth.OAuthRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetOAuthAttemptStatusUseCase @Inject constructor(
        private val repository: OAuthRepository,
        networkHandler: NetworkHandler
) : UseCase<OAuthAttempt, String>(networkHandler) {

    override fun run(params: String) = repository.getOAuthAttemptStatus(attemptId = params)
}
