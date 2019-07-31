package com.aptopayments.core.repository.card.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aptopayments.core.repository.card.local.entities.CardLocalEntity

@Dao
interface CardLocalDao {

    @Insert
    fun saveCard(cardLocalEntity: CardLocalEntity)

    @Query("SELECT * FROM card WHERE account_id = :cardId")
    fun getCard(cardId: String): CardLocalEntity?

    @Query("DELETE FROM card")
    fun clearCardCache()
}
