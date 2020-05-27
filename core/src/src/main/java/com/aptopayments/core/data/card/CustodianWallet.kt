package com.aptopayments.core.data.card

data class CustodianWallet(
    val type: String,
    val custodian: Custodian,
    val balance: Money?
)
