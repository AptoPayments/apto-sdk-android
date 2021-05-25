package com.aptopayments.mobile.repository

import android.content.Context
import android.content.SharedPreferences
import com.aptopayments.mobile.db.LocalDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val PREF_USER_TOKEN_FILENAME = "com.shiftpayments.sdk.usertoken"
private const val PREF_USER_TOKEN = "PREF_USER_TOKEN"

interface UserSessionRepository {
    var userToken: String
    fun isUserSessionValid(): Boolean
    fun clearUserSession()
    fun subscribeSessionInvalidListener(instance: Any, callback: (String) -> Unit)
    fun unsubscribeSessionInvalidListener(instance: Any)
    fun subscribeNewSessionListener(instance: Any, callback: () -> Unit)
    fun unsubscribeNewSessionListener(instance: Any)
}

internal class UserSessionRepositoryImpl(context: Context, private val localDB: LocalDB) : UserSessionRepository {

    private val sharedPref: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREF_USER_TOKEN_FILENAME, Context.MODE_PRIVATE
        )
    }

    private var sessionInvalidEventListeners: MutableMap<Any, (String) -> Unit> = mutableMapOf()
    private var newSessionEventListeners: MutableMap<Any, () -> Unit> = mutableMapOf()
    private var mUserToken: String = ""

    override var userToken: String
        get() {
            if (mUserToken.isEmpty()) {
                mUserToken = sharedPref.getString(PREF_USER_TOKEN, "")!!
            }
            return mUserToken
        }
        set(value) {
            if (value.isEmpty()) {
                notifySessionInvalidListeners(mUserToken)
                clearDataBaseCache()
                mUserToken = value
            } else {
                mUserToken = value
                notifyNewSessionListeners()
            }
            sharedPref.edit().putString(PREF_USER_TOKEN, value).apply()
        }

    override fun isUserSessionValid(): Boolean = userToken.isNotEmpty()

    private fun clearDataBaseCache() = GlobalScope.launch(Dispatchers.IO) { localDB.clearAllTables() }

    override fun clearUserSession() {
        if (userToken.isNotEmpty()) userToken = ""
    }

    override fun subscribeSessionInvalidListener(instance: Any, callback: (String) -> Unit) {
        sessionInvalidEventListeners[instance] = callback
    }

    override fun unsubscribeSessionInvalidListener(instance: Any) {
        sessionInvalidEventListeners.remove(instance)
    }

    private fun notifySessionInvalidListeners(userToken: String) {
        val copy = sessionInvalidEventListeners.toMutableMap()
        copy.iterator().forEach { it.value.invoke(userToken) }
        copy.clear()
    }

    override fun subscribeNewSessionListener(instance: Any, callback: () -> Unit) {
        newSessionEventListeners[instance] = callback
    }

    override fun unsubscribeNewSessionListener(instance: Any) {
        newSessionEventListeners.remove(instance)
    }

    private fun notifyNewSessionListeners() = newSessionEventListeners.forEach { it.value.invoke() }
}
