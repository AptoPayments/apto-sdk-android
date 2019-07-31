package com.aptopayments.core.repository

import android.content.Context
import android.content.SharedPreferences

private const val PREF_USER_TOKEN_FILENAME = "com.aptopayments.sdk.usertoken"
private const val PREF_USER_TOKEN = "PREF_USER_TOKEN"

class UserSessionRepository(context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(
            PREF_USER_TOKEN_FILENAME, Context.MODE_PRIVATE)

    private var sessionInvalidEventListeners: MutableMap<Any, (String) -> Unit> = mutableMapOf()
    private var newSessionEventListeners: MutableMap<Any, () -> Unit> = mutableMapOf()
    private var mUserToken: String = ""

    enum class UserSession {
        VALID,
        INVALID;

        fun isValid() = this == VALID
    }

    var userSession: UserSession = if (userToken.isEmpty()) UserSession.INVALID else UserSession.VALID

    var userToken: String
        get() {
            if(mUserToken.isEmpty()) {
                mUserToken = sharedPref.getString(PREF_USER_TOKEN, "")
            }
            return mUserToken
        }
        set(value) {
            if (value.isEmpty()) {
                notifySessionInvalidListeners(mUserToken)
                mUserToken = value
            } else {
                mUserToken = value
                notifyNewSessionListeners()
            }
            sharedPref.edit().putString(PREF_USER_TOKEN, value).apply()
        }

    fun clearUserSession() {
        if (userToken.isNotEmpty()) userToken = ""
    }

    fun subscribeSessionInvalidListener(instance: Any, callback: (String)->Unit) {
        sessionInvalidEventListeners[instance] = callback
    }

    fun unsubscribeSessionInvalidListener(instance: Any) =
            sessionInvalidEventListeners.remove(instance)

    private fun notifySessionInvalidListeners(userToken: String) =
            sessionInvalidEventListeners.iterator().forEach { it.value.invoke(userToken) }

    fun subscribeNewSessionListener(instance: Any, callback: ()->Unit) {
        newSessionEventListeners[instance] = callback
    }

    fun unsubscribeNewSessionListener(instance: Any) =
            newSessionEventListeners.remove(instance)

    private fun notifyNewSessionListeners() =
            newSessionEventListeners.iterator().forEach { it.value.invoke() }
}
