package com.aptopayments.mobile.data.workflowaction

import java.io.Serializable

enum class WorkflowActionType {
    UNSUPPORTED_ACTION_TYPE,
    SELECT_BALANCE_STORE,
    ISSUE_CARD,
    SHOW_DISCLAIMER,
    COLLECT_USER_DATA
}

sealed class WorkflowAction(
    val actionId: String,
    open val configuration: WorkflowActionConfiguration? = null,
    val labels: Map<String, String>? = null
) : Serializable {
    class SelectBalanceStoreAction(
        actionId: String,
        override val configuration: WorkflowActionConfigurationSelectBalanceStore?,
        labels: Map<String, String>?
    ) : WorkflowAction(actionId, configuration, labels)

    class IssueCardAction(
        actionId: String,
        override val configuration: WorkflowActionConfigurationIssueCard?,
        labels: Map<String, String>?
    ) : WorkflowAction(actionId, configuration, labels)

    class ShowDisclaimerAction(
        actionId: String,
        override val configuration: WorkflowActionConfigurationShowDisclaimer?,
        labels: Map<String, String>?
    ) : WorkflowAction(actionId, configuration, labels)

    class CollectUserDataAction(
        actionId: String,
        override val configuration: WorkflowActionConfigurationCollectUserData?,
        labels: Map<String, String>?
    ) : WorkflowAction(actionId, configuration, labels)

    class UnsupportedActionType(actionId: String) : WorkflowAction(
        actionId, null, null
    )
}
