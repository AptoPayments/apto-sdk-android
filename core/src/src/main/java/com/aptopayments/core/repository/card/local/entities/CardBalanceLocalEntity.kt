package com.aptopayments.core.repository.card.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aptopayments.core.data.card.CustodianWallet
import com.aptopayments.core.data.card.Money
import com.aptopayments.core.data.fundingsources.Balance

@Entity(tableName = "card_balance")
internal class CardBalanceLocalEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "card_id")
    var cardId: String,

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
        fun fromBalance(cardId: String, balance: Balance): CardBalanceLocalEntity {
            return CardBalanceLocalEntity(
                cardId = cardId,
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
}
