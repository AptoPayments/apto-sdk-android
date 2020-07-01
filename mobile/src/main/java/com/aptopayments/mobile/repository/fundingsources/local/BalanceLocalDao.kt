package com.aptopayments.mobile.repository.fundingsources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aptopayments.mobile.repository.fundingsources.local.entities.BalanceLocalEntity

@Dao
internal interface BalanceLocalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveBalances(balances: List<BalanceLocalEntity>)

    @Query("SELECT * FROM `balance`")
    fun getBalances(): List<BalanceLocalEntity>?

    @Query("DELETE FROM `balance`")
    fun clearBalanceCache()
}
