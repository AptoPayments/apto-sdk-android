package com.aptopayments.core.repository

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

private const val PREF_USER_PREFERENCES_FILENAME = "com.aptopayments.sdk.userpreferences"
private const val PREF_SHOW_DETAILED_ACTIVITY = "PREF_SHOW_DETAILED_ACTIVITY"

class UserPreferencesRepository @Inject constructor(
        private val userSessionRepository: UserSessionRepository,
        context: Context
) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(
            PREF_USER_PREFERENCES_FILENAME, Context.MODE_PRIVATE)

    var showDetailedCardActivity: Boolean
        get() = sharedPref.getBoolean(PREF_SHOW_DETAILED_ACTIVITY, false)
        set(value) {
            sharedPref.edit().putBoolean(PREF_SHOW_DETAILED_ACTIVITY, value).apply()
        }

    init {
        userSessionRepository.subscribeSessionInvalidListener(this) {
            sharedPref.edit().clear().apply()
        }
    }

    protected fun finalize() {
        userSessionRepository.unsubscribeSessionInvalidListener(this)
    }
}
