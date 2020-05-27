package com.aptopayments.core.data.card

import com.aptopayments.core.data.PhoneNumber
import java.io.Serializable

data class Activation(
    val ivrPhone: PhoneNumber?
) : Serializable
