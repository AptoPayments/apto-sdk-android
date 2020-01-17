package com.aptopayments.core.repository.fundingsources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.aptopayments.core.repository.fundingsources.local.entities.BalanceLocalEntity

@Dao
internal interface BalanceLocalDao {

    @Insert
    fun saveBalances(balances: List<BalanceLocalEntity>)

    @Query("SELECT * FROM `balance`")
    fun getBalances(): List<BalanceLocalEntity>?

    @Query("DELETE FROM `balance`")
    fun clearBalanceCache()
}
