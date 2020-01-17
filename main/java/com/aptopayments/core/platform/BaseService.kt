package com.aptopayments.core.platform

import com.aptopayments.core.repository.UserSessionRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

internal open class BaseService : KoinComponent {
    val userSessionRepository: UserSessionRepository by inject()

    protected fun authorizationHeader(userToken: String) = "Bearer $userToken"
}
