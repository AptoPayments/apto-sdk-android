package com.aptopayments.core.repository.fundingsources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.aptopayments.core.data.card.CustodianWallet
import com.aptopayments.core.data.card.Money
import com.aptopayments.core.data.fundingsources.Balance
import com.aptopayments.core.network.GsonProvider

@Entity(tableName = "balance")
class BalanceLocalEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "state")
    var state: Balance.BalanceState? = null,

    @ColumnInfo(name = "type")
    var type: String,

    @ColumnInfo(name = "funding_source_type")
    var fundingSourceType: String,

    @ColumnInfo(name = "balance")
    var balance: Money? = null,

    @ColumnInfo(name = "amount_spendable")
    var amountSpendable: Money? = null,

    @ColumnInfo(name = "amount_held")
    var amountHeld: Money? = null,

    @ColumnInfo(name = "details")
    var custodianWallet: CustodianWallet? = null

) {
    fun toBalance() = Balance(
        id = id,
        state = state,
        type = type,
        fundingSourceType = fundingSourceType,
        balance = balance,
        amountSpendable = amountSpendable,
        amountHeld = amountHeld,
        custodianWallet = custodianWallet
    )

    companion object {
        fun fromBalance(balance: Balance): BalanceLocalEntity {
            return BalanceLocalEntity(
                id = balance.id,
                state = balance.state,
                type = balance.type,
                fundingSourceType = balance.fundingSourceType,
                balance = balance.balance,
                amountSpendable = balance.amountSpendable,
                amountHeld = balance.amountHeld,
                custodianWallet = balance.custodianWallet
            )
        }
    }

    class Converters {

        @TypeConverter
        fun stringToCustodianWallet(value: String?): CustodianWallet? =
            GsonProvider.provide().fromJson(value, CustodianWallet::class.java)

        @TypeConverter
        fun custodianWalletToString(custodianWallet: CustodianWallet?): String? =
            GsonProvider.provide().toJson(custodianWallet)

        @TypeConverter
        fun stringToBalanceState(value: String?): Balance.BalanceState? =
            GsonProvider.provide().fromJson(value, Balance.BalanceState::class.java)

        @TypeConverter
        fun balanceStateToString(balanceState: Balance.BalanceState?): String? =
            GsonProvider.provide().toJson(balanceState)
    }
}
