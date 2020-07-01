package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.workflowaction.WorkflowAction
import com.aptopayments.mobile.data.workflowaction.WorkflowActionType
import com.aptopayments.mobile.repository.LiteralsRepository
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal data class WorkflowActionEntity(

    @SerializedName("action_id")
    var actionId: String = "",

    @SerializedName("action_type")
    var actionType: String = "",

    @SerializedName("configuration")
    var configuration: WorkflowActionConfigurationEntity? = null,

    @SerializedName("labels")
    var labels: Map<String, String>? = null
) {
    fun toWorkflowAction(): WorkflowAction {
        labels?.let { LiteralsRepository.appendServerLiterals(it) }
        return WorkflowAction(
            actionId = actionId,
            actionType = parseActionType(actionType),
            configuration = configuration?.toWorkflowActionConfiguration(),
            labels = labels
        )
    }

    private fun parseActionType(type: String): WorkflowActionType {
        return try {
            WorkflowActionType.valueOf(type.toUpperCase(Locale.US))
        } catch (exception: Throwable) {
            WorkflowActionType.UNSUPPORTED_ACTION_TYPE
        }
    }
}
