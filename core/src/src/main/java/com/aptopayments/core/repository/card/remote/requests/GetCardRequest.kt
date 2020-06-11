package com.aptopayments.core.repository.card.remote.requests

import java.io.Serializable

internal data class GetCardRequest(val accountID: String) : Serializable
