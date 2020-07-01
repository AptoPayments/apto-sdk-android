package com.aptopayments.mobile.repository

class UserSessionRepositoryDouble : UserSessionRepository {
    override var userToken: String = ""

    override fun isUserSessionValid() = userToken.isNotEmpty()

    override fun clearUserSession() {}

    override fun subscribeSessionInvalidListener(instance: Any, callback: (String) -> Unit) {}

    override fun unsubscribeSessionInvalidListener(instance: Any) {}

    override fun subscribeNewSessionListener(instance: Any, callback: () -> Unit) {}

    override fun unsubscribeNewSessionListener(instance: Any) {}
}
