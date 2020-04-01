package com.aptopayments.core.repository.fundingsources.remote.entities

import com.aptopayments.core.data.fundingsources.Balance
import com.aptopayments.core.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal class BalanceEntity(

        @SerializedName("id")
        var id: String = "",

        @SerializedName("state")
        var state: String = "",

        @SerializedName("type")
        var type: String = "",

        @SerializedName("funding_source_type")
        var fundingSourceType: String = "",

        @SerializedName("balance")
        var balance: MoneyEntity? = null,

        @SerializedName("amount_spendable")
        var amountSpendable: MoneyEntity? = null,

        @SerializedName("amount_held")
        var amountHeld: MoneyEntity? = null,

        @SerializedName("details")
        var custodianWallet: CustodianWalletEntity? = null

) {
    fun toBalance() = Balance(
            id = id,
            state = parseBalanceState(state),
            type = type,
            fundingSourceType = fundingSourceType,
            balance = balance?.toMoney(),
            amountSpendable = amountSpendable?.toMoney(),
            amountHeld = amountHeld?.toMoney(),
            custodianWallet = custodianWallet?.toCustodianWallet()
    )

    private fun parseBalanceState(state: String?): Balance.BalanceState? {
        return state?.let {
            try {
                Balance.BalanceState.valueOf(it.toUpperCase(Locale.US))
            } catch (exception: Throwable) {
                Balance.BalanceState.INVALID
            }
        }
    }
}
