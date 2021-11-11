package com.aptopayments.mobile.repository.fundingsources.remote.entities

import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal class BalanceEntity(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("state")
    val state: String = "",

    @SerializedName("type")
    val type: String = "",

    @SerializedName("funding_source_type")
    val fundingSourceType: String = "",

    @SerializedName("balance")
    val balance: MoneyEntity? = null,

    @SerializedName("amount_spendable")
    val amountSpendable: MoneyEntity? = null,

    @SerializedName("amount_held")
    val amountHeld: MoneyEntity? = null,

    @SerializedName("details")
    val custodianWallet: CustodianWalletEntity? = null

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
                Balance.BalanceState.valueOf(it.uppercase(Locale.US))
            } catch (exception: IllegalArgumentException) {
                Balance.BalanceState.INVALID
            }
        }
    }
}
