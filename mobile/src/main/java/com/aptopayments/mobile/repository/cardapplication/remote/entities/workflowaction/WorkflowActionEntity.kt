package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.workflowaction.*
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

        return when (parseActionType(actionType)) {
            WorkflowActionType.SELECT_BALANCE_STORE -> createSelectBalanceStore()
            WorkflowActionType.ISSUE_CARD -> buildIssueCard()
            WorkflowActionType.SHOW_DISCLAIMER -> buildShowDisclaimer()
            WorkflowActionType.COLLECT_USER_DATA -> buildCollectUserData()
            else -> WorkflowAction.UnsupportedActionType(
                actionId = actionId
            )
        }
    }

    private fun buildCollectUserData(): WorkflowAction.CollectUserDataAction {
        return WorkflowAction.CollectUserDataAction(
            actionId,
            configuration?.toWorkflowActionConfiguration() as? WorkflowActionConfigurationCollectUserData,
            labels
        )
    }

    private fun buildShowDisclaimer(): WorkflowAction.ShowDisclaimerAction {
        return WorkflowAction.ShowDisclaimerAction(
            actionId,
            configuration?.toWorkflowActionConfiguration() as? WorkflowActionConfigurationShowDisclaimer,
            labels
        )
    }

    private fun buildIssueCard(): WorkflowAction.IssueCardAction {
        return WorkflowAction.IssueCardAction(
            actionId,
            configuration?.toWorkflowActionConfiguration() as? WorkflowActionConfigurationIssueCard,
            labels
        )
    }

    private fun createSelectBalanceStore(): WorkflowAction.SelectBalanceStoreAction {
        return WorkflowAction.SelectBalanceStoreAction(
            actionId,
            configuration?.toWorkflowActionConfiguration() as? WorkflowActionConfigurationSelectBalanceStore,
            labels
        )
    }

    private fun parseActionType(type: String): WorkflowActionType {
        return try {
            WorkflowActionType.valueOf(type.uppercase(Locale.US))
        } catch (exception: IllegalArgumentException) {
            WorkflowActionType.UNSUPPORTED_ACTION_TYPE
        }
    }
}
