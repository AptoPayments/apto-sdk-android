package com.aptopayments.core.platform

import com.aptopayments.core.repository.UserSessionRepository
import javax.inject.Inject

internal open class BaseService {

    @Inject lateinit var userSessionRepository: UserSessionRepository

    protected fun authorizationHeader(userToken: String) = "Bearer $userToken"
}
