package com.aptopayments.mobile.repository.paymentsources.remote.entities

import com.aptopayments.mobile.data.paymentsources.BankAccount
import com.google.gson.annotations.SerializedName

internal data class BankAccountEntity(
    @SerializedName("id")
    override val id: String,
    @SerializedName("description")
    override val description: String?,
    @SerializedName("is_preferred")
    override val isPreferred: Boolean = false
) : PaymentSourceEntity {

    override fun toPaymentSource() = BankAccount(
        id = id,
        description = description,
        isPreferred = isPreferred
    )
}
