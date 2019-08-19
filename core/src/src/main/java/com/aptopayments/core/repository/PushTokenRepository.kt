package com.aptopayments.core.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import com.aptopayments.core.repository.user.usecases.RegisterPushDeviceUseCase
import com.aptopayments.core.repository.user.usecases.UnregisterPushDeviceParams
import com.aptopayments.core.repository.user.usecases.UnregisterPushDeviceUseCase
import java.lang.reflect.Modifier

private const val PREF_PUSH_TOKEN_FILENAME = "com.aptopayments.sdk.pushtoken"
private const val PREF_PUSH_TOKEN = "PREF_PUSH_TOKEN"

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class PushTokenRepository constructor(
        private val userSessionRepository: UserSessionRepository,
        private var registerPushDeviceUseCase: RegisterPushDeviceUseCase,
        private var unregisterPushDeviceUseCase: UnregisterPushDeviceUseCase,
        val context: Context
) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(
            PREF_PUSH_TOKEN_FILENAME, Context.MODE_PRIVATE)

    var pushToken: String
        get() = sharedPref.getString(PREF_PUSH_TOKEN, "")!!
        set(token) {
            sharedPref.edit().putString(PREF_PUSH_TOKEN, token).apply()
            if (userSessionRepository.userSession.isValid()) registerPushDeviceUseCase(pushToken)
        }

    init {
        subscribeToNewSessionEvent()
        subscribeToSessionInvalidEvent()
    }

    protected fun finalize() {
        userSessionRepository.unsubscribeNewSessionListener(this)
        userSessionRepository.unsubscribeSessionInvalidListener(this)
    }

    private fun subscribeToSessionInvalidEvent() {
        userSessionRepository.subscribeSessionInvalidListener(this) {
            if (pushToken.isNotEmpty()) unregisterPushDeviceUseCase(UnregisterPushDeviceParams(it, pushToken))
        }
    }

    private fun subscribeToNewSessionEvent() {
        userSessionRepository.subscribeNewSessionListener(this) {
            if (pushToken.isNotEmpty()) {
                registerPushDeviceUseCase(pushToken) {
                    userSessionRepository.unsubscribeNewSessionListener(this)
                }
            }
        }
    }
}
