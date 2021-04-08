package com.aptopayments.mobile.exception.server

import com.aptopayments.mobile.exception.Failure

class ErrorBalanceValidationsInsufficientApplicationLimit(message: String?) : Failure.ServerError(
    ServerErrorCodes.BALANCE_VALIDATIONS_INSUFFICIENT_APPLICATION_LIMIT,
    message,
    "select_balance_store.login.error_insufficient_application_limit.message"
)
