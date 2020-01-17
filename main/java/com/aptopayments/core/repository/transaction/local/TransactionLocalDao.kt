package com.aptopayments.core.repository.transaction.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aptopayments.core.repository.transaction.local.entities.TransactionLocalEntity

@Dao
internal interface TransactionLocalDao {

    @Insert
    fun saveTransactions(transactions: List<TransactionLocalEntity>)

    @Query("SELECT * FROM `transaction` WHERE account_id = :cardId")
    fun getTransactions(cardId: String): List<TransactionLocalEntity>?

    @Query("DELETE FROM `transaction`")
    fun clearTransactionCache()
}
