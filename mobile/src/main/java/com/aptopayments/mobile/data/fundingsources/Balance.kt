package com.aptopayments.mobile.data.fundingsources

import com.aptopayments.mobile.data.card.CustodianWallet
import com.aptopayments.mobile.data.card.Money

data class Balance(
    var id: String = "",
    var state: BalanceState? = null,
    var type: String = "",
    var fundingSourceType: String = "",
    var balance: Money? = null,
    var amountSpendable: Money? = null,
    var amountHeld: Money? = null,
    var custodianWallet: CustodianWallet? = null
) {
    enum class BalanceState {
        VALID,
        INVALID
    }
}
