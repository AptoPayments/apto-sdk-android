package com.aptopayments.core.data.workflowaction

import java.io.Serializable

enum class WorkflowActionType {
    UNSUPPORTED_ACTION_TYPE,
    SELECT_BALANCE_STORE,
    ISSUE_CARD,
    SHOW_DISCLAIMER,
    COLLECT_USER_DATA
}

data class WorkflowAction (
        var actionId: String,
        var actionType: WorkflowActionType = WorkflowActionType.UNSUPPORTED_ACTION_TYPE,
        var configuration: WorkflowActionConfiguration? = null,
        var labels: Map<String,String>? = null
) : Serializable
