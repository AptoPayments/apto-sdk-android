package com.aptopayments.core.repository.user.usecases

import com.aptopayments.core.data.user.notificationpreferences.NotificationGroup
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository

internal class UpdateNotificationPreferencesUseCase constructor(
        private val repository: UserRepository,
        networkHandler: NetworkHandler
) : UseCase<Unit, List<NotificationGroup>>(networkHandler) {

    override fun run(params: List<NotificationGroup>) = repository.updateNotificationPreferences(params)
}
