package com.aptopayments.mobile.exception.server

import com.aptopayments.mobile.exception.Failure

class ErrorOauthTokenRevoked(message: String?) : Failure.ServerError(
    ServerErrorCodes.REVOKED_TOKEN, message, "issue_card.issue_card.error.token_revoked"
)
