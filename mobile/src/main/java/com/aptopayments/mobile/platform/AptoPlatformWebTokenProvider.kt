package com.aptopayments.mobile.platform

import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either

interface AptoPlatformWebTokenProvider {
    fun getToken(payload: String, callback: (Either<WebTokenFailure, String>) -> Unit)
}

object WebTokenFailure : Failure.FeatureFailure("error_transport_undefined")
