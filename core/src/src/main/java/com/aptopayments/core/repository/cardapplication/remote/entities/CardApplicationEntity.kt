package com.aptopayments.core.repository.cardapplication.remote.entities

import com.aptopayments.core.data.card.CardApplication
import com.aptopayments.core.data.card.CardApplicationStatus
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionEntity
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal data class CardApplicationEntity(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("status")
    val status: String = "",

    @SerializedName("workflow_object_id")
    val workflowObjectId: String = "",

    @SerializedName("next_action")
    val workflowAction: WorkflowActionEntity? = null

) {
    fun toCardApplication() = CardApplication(
        id = id,
        status = parseCardApplicationStatus(status),
        workflowObjectId = workflowObjectId,
        nextAction = workflowAction?.toWorkflowAction()
    )

    private fun parseCardApplicationStatus(status: String): CardApplicationStatus {
        return try {
            CardApplicationStatus.valueOf(status.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            CardApplicationStatus.UNKNOWN
        }
    }
}
