package com.aptopayments.mobile.data.card

data class CustodianWallet(
    val type: String,
    val custodian: Custodian,
    val balance: Money?
)
