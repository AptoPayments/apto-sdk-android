package com.aptopayments.core.repository.voip.usecases

import com.aptopayments.core.data.voip.Action

data class SetupVoipCallParams (
        val cardId: String,
        val action: Action
)
