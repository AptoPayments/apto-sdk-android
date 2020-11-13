package com.aptopayments.mobile.data.voip

import androidx.annotation.RestrictTo

@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
enum class Action(val source: String) {
    LISTEN_PIN("listen_pin"),
    CUSTOMER_SUPPORT("customer_support")
}
