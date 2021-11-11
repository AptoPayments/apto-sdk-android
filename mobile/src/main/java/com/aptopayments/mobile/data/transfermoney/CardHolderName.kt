package com.aptopayments.mobile.data.transfermoney

import java.io.Serializable

data class CardHolderName(val firstName: String, val lastName: String) : Serializable {
    override fun toString() = "$firstName $lastName"
}
