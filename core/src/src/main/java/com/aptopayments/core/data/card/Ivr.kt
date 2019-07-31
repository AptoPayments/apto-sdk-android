package com.aptopayments.core.data.card

import com.aptopayments.core.data.PhoneNumber
import java.io.Serializable

data class Ivr(
        val status: FeatureStatus,
        val ivrPhone: PhoneNumber?
) : Serializable
