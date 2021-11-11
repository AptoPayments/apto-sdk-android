package com.aptopayments.mobile.data.transfermoney

import java.io.Serializable

data class CardHolderData(val name: CardHolderName, val cardholderId: String) : Serializable
