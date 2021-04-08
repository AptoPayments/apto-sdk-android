package com.aptopayments.mobile.exception.server

import com.aptopayments.mobile.exception.Failure

class ErrorInsufficientFunds(message: String? = null) : Failure.ServerError(
    ServerErrorCodes.INSUFFICIENT_FUNDS,
    message,
    "error.transport.undefined"
)
