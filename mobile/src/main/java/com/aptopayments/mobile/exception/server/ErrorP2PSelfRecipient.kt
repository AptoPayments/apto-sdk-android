package com.aptopayments.mobile.exception.server

import com.aptopayments.mobile.exception.Failure

class ErrorP2PSelfRecipient(message: String? = null) : Failure.ServerError(
    ServerErrorCodes.P2P_SELF_SEND, message, "p2p_transfer_error_self_transfer"
)
