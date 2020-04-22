package com.aptopayments.core.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aptopayments.core.repository.card.local.CardBalanceLocalDao
import com.aptopayments.core.repository.card.local.entities.CardBalanceLocalEntity
import com.aptopayments.core.repository.fundingsources.local.BalanceLocalDao
import com.aptopayments.core.repository.fundingsources.local.entities.BalanceLocalEntity
import com.aptopayments.core.repository.transaction.local.TransactionLocalDao
import com.aptopayments.core.repository.transaction.local.entities.TransactionLocalEntity

private const val DATABASE_NAME = "local_db"
private const val DB_VERSION = 14

@Database(entities = [
        TransactionLocalEntity::class,
        CardBalanceLocalEntity::class,
        BalanceLocalEntity::class
], version = DB_VERSION, exportSchema = false)
@TypeConverters(
        Converters::class,
        TransactionLocalEntity.Converters::class,
        BalanceLocalEntity.Converters::class
)
internal abstract class LocalDB : RoomDatabase() {
    abstract fun cardBalanceLocalDao(): CardBalanceLocalDao
    abstract fun transactionLocalDao(): TransactionLocalDao
    abstract fun balanceLocalDao(): BalanceLocalDao
}

internal object DataBaseProvider {

    private var cacheDB: LocalDB? = null

    @Synchronized
    fun getInstance(context: Context): LocalDB {
        if (cacheDB == null) {
            cacheDB = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDB::class.java,
                    DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
        }
        return cacheDB!!
    }
}
