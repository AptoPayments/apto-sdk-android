package com.aptopayments.core.repository.user.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.user.notificationpreferences.NotificationPreferences
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetNotificationPreferencesUseCase @Inject constructor(
        private val repository: UserRepository,
        networkHandler: NetworkHandler
) : UseCase<NotificationPreferences, Unit>(networkHandler) {

    override fun run(params: Unit) = repository.getNotificationPreferences()
}
