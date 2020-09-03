package com.aptopayments.mobile.repository.card.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.network.GsonProvider
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity

const val SECURE_FILE_NAME = "com.aptopayments.core.repository.local.secure.txt"

const val KEY = "card_key"

internal interface CardLocalRepository {
    fun saveCard(card: Card)
    fun getCard(cardId: String): Card?
    fun clearCardCache()
}

internal class CardLocalRepositoryImpl(context: Context) : CardLocalRepository {

    private val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
    private val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

    private val pref = EncryptedSharedPreferences
        .create(
            SECURE_FILE_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    override fun saveCard(card: Card) {
        pref.edit().putString(KEY, GsonProvider.provide().toJson(CardEntity.from(card))).apply()
    }

    override fun getCard(cardId: String): Card? {
        val card = getCardFromStorage()
        return if (card != null && card.accountID == cardId) card else null
    }

    private fun getCardFromStorage(): Card? {
        return try {
            val cardString = pref.getString(KEY, null)
            val cardEntity = GsonProvider.provide().fromJson(cardString, CardEntity::class.java)
            return cardEntity.toCard()
        } catch (e: Exception) {
            null
        }
    }

    override fun clearCardCache() {
        pref.edit().clear().apply()
    }
}
