package com.aptopayments.mobile.exception.server

import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.exception.server.ServerErrorCodes.BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED

class ErrorBalanceValidationsEmailSendsDisabled(message: String?) : Failure.ServerError(
    BALANCE_VALIDATIONS_EMAIL_SENDS_DISABLED, message, "select_balance_store.login.error_email_sends_disabled.message"
)
