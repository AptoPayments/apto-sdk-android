package com.aptopayments.mobile.data.card

import com.aptopayments.mobile.data.PhoneNumber
import java.io.Serializable

data class Activation(
    val ivrPhone: PhoneNumber?
) : Serializable
