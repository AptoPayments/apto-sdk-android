package com.aptopayments.core.repository

import android.content.Context
import java.util.*

object LiteralsRepository {

    private var serverLiterals: MutableMap<String, String> = HashMap()

    fun appendServerLiterals(serverLiterals: Map<String, String>) {
        this.serverLiterals.putAll(serverLiterals.mapKeys {
            it.key.replace(".", "_")
        })
    }

    fun localized(context: Context, key: String): String {
        val curatedKey = key.replace(".", "_")
        return serverLiterals[curatedKey]
                ?: return try {
                    context.resources.getString(context.resources.getIdentifier(
                            curatedKey,
                            "string",
                            context.packageName)
                    )
                } catch (exception: Throwable) {
                    key
                }
    }
}
