package com.aptopayments.mobile.data.card

import com.aptopayments.mobile.data.fundingsources.AchAccountDetails
import java.io.Serializable

data class AchAccountFeature(
    val isEnabled: Boolean = false,
    val isAccountProvisioned: Boolean = false,
    val disclaimer: Disclaimer? = null,
    val accountDetails: AchAccountDetails? = null,
) : Serializable
