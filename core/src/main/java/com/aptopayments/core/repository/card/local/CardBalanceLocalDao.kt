package com.aptopayments.core.repository.card.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aptopayments.core.repository.card.local.entities.CardBalanceLocalEntity

@Dao
interface CardBalanceLocalDao {

    @Insert
    fun saveCardBalance(cardBalanceLocalEntity: CardBalanceLocalEntity)

    @Query("SELECT * FROM card_balance WHERE card_id = :cardId")
    fun getCardBalance(cardId: String): CardBalanceLocalEntity?

    @Query("DELETE FROM card_balance")
    fun clearCardBalanceCache()
}
