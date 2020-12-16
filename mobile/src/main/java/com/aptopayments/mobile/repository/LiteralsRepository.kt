package com.aptopayments.mobile.repository

import com.aptopayments.mobile.platform.AptoPlatform
import java.util.*

object LiteralsRepository {

    private val serverLiterals: MutableMap<String, String> = HashMap()

    fun appendServerLiterals(serverLiterals: Map<String, String>) {
        this.serverLiterals.putAll(
            serverLiterals.mapKeys {
                it.key.replace(".", "_")
            }
        )
    }

    fun localized(key: String): String {
        val curatedKey = key.replace(".", "_")
        return serverLiterals[curatedKey]
            ?: return try {
                val context = AptoPlatform.application.applicationContext
                context.resources.getString(context.resources.getIdentifier(curatedKey, "string", context.packageName))
            } catch (exception: Throwable) {
                key
            }
    }
}
