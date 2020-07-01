package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.data.user.notificationpreferences.NotificationPreferences
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.UserRepository

internal class GetNotificationPreferencesUseCase constructor(
    private val repository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<NotificationPreferences, Unit>(networkHandler) {

    override fun run(params: Unit) = repository.getNotificationPreferences()
}
