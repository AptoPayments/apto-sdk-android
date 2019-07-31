package com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.core.data.workflowaction.WorkflowAction
import com.aptopayments.core.data.workflowaction.WorkflowActionType
import com.aptopayments.sdk.core.repository.LiteralsRepository
import com.google.gson.annotations.SerializedName

internal data class WorkflowActionEntity (

        @SerializedName("action_id")
        var actionId: String = "",

        @SerializedName("action_type")
        var actionType: String = "",

        @SerializedName("configuration")
        var configuration: WorkflowActionConfigurationEntity? = null,

        @SerializedName("labels")
        var labels: Map<String,String>? = null
) {
    fun toWorkflowAction(): WorkflowAction {
        labels?.let { LiteralsRepository.appendServerLiterals(it) }
        return WorkflowAction(
                actionId=actionId,
                actionType = parseActionType(actionType),
                configuration = configuration?.toWorkflowActionConfiguration(),
                labels = labels
        )
    }

    private fun parseActionType(type: String): WorkflowActionType {
        return try {
            WorkflowActionType.valueOf(type.toUpperCase())
        } catch (exception: Throwable) {
            WorkflowActionType.UNSUPPORTED_ACTION_TYPE
        }
    }
}
