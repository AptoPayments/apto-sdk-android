package com.aptopayments.mobile.repository.voip.usecases

import com.aptopayments.mobile.data.voip.Action

internal data class SetupVoipCallParams(
    val cardId: String,
    val action: Action
)
