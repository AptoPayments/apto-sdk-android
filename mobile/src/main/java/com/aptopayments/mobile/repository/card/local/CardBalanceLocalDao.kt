package com.aptopayments.mobile.repository.card.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aptopayments.mobile.repository.card.local.entities.CardBalanceLocalEntity

@Dao
internal interface CardBalanceLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCardBalance(cardBalanceLocalEntity: CardBalanceLocalEntity)

    @Query("SELECT * FROM card_balance WHERE card_id = :cardId")
    fun getCardBalance(cardId: String): CardBalanceLocalEntity?

    @Query("DELETE FROM card_balance")
    fun clearCardBalanceCache()
}
