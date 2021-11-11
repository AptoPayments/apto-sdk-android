package com.aptopayments.mobile.exception.server

import com.aptopayments.mobile.exception.Failure

class ErrorRecipientNotFound(message: String?) : Failure.ServerError(
    ServerErrorCodes.RECIPIENT_NOT_FOUND, message, "p2p_transfer_error_recipient_not_found"
)
