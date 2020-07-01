package com.aptopayments.mobile.data.card

import com.aptopayments.mobile.data.workflowaction.WorkflowAction
import java.io.Serializable

enum class CardApplicationStatus {
    UNKNOWN,
    CREATED,
    PENDING_KYC,
    APPROVED,
    REJECTED
}

data class CardApplication(
    val id: String,
    val status: CardApplicationStatus,
    val workflowObjectId: String,
    val nextAction: WorkflowAction?
) : Serializable
