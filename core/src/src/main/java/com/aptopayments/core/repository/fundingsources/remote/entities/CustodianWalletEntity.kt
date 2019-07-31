package com.aptopayments.core.repository.fundingsources.remote.entities

import com.aptopayments.core.data.card.CustodianWallet
import com.aptopayments.core.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName

internal class CustodianWalletEntity(

        @SerializedName("type")
        var type: String,

        @SerializedName("custodian")
        var custodian: CustodianEntity,

        @SerializedName("balance")
        var balance: MoneyEntity? = null
        ) {
    fun toCustodianWallet() = CustodianWallet(
            type = type,
            custodian = custodian.toCustodian(),
            balance = balance?.toMoney()
    )
}
