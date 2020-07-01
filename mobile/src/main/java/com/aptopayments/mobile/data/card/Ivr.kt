package com.aptopayments.mobile.data.card

import com.aptopayments.mobile.data.PhoneNumber
import java.io.Serializable

data class Ivr(
    val status: FeatureStatus,
    val ivrPhone: PhoneNumber?
) : Serializable
