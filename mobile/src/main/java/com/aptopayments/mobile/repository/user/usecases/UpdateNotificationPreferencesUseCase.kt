package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.data.user.notificationpreferences.NotificationGroup
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.UserRepository

internal class UpdateNotificationPreferencesUseCase constructor(
    private val repository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<Unit, List<NotificationGroup>>(networkHandler) {

    override fun run(params: List<NotificationGroup>) = repository.updateNotificationPreferences(params)
}
