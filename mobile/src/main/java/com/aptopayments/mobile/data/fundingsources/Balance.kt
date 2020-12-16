package com.aptopayments.mobile.data.fundingsources

import com.aptopayments.mobile.data.card.CustodianWallet
import com.aptopayments.mobile.data.card.Money

data class Balance(
    val id: String = "",
    val state: BalanceState? = null,
    val type: String = "",
    val fundingSourceType: String = "",
    val balance: Money? = null,
    val amountSpendable: Money? = null,
    val amountHeld: Money? = null,
    val custodianWallet: CustodianWallet? = null
) {
    enum class BalanceState {
        VALID,
        INVALID
    }
}
