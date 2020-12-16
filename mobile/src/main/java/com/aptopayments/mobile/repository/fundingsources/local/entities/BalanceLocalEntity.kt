package com.aptopayments.mobile.repository.fundingsources.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.aptopayments.mobile.data.card.CustodianWallet
import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.network.GsonProvider

@Entity(tableName = "balance")
internal class BalanceLocalEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "state")
    val state: Balance.BalanceState? = null,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "funding_source_type")
    val fundingSourceType: String,

    @ColumnInfo(name = "balance")
    val balance: Money? = null,

    @ColumnInfo(name = "amount_spendable")
    val amountSpendable: Money? = null,

    @ColumnInfo(name = "amount_held")
    val amountHeld: Money? = null,

    @ColumnInfo(name = "details")
    val custodianWallet: CustodianWallet? = null

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
