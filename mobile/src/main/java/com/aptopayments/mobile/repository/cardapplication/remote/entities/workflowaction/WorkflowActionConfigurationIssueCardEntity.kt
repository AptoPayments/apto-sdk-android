package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.workflowaction.WorkflowActionConfiguration
import com.aptopayments.mobile.data.workflowaction.WorkflowActionConfigurationIssueCard
import com.google.gson.annotations.SerializedName

internal class WorkflowActionConfigurationIssueCardEntity(

    @SerializedName("error_asset")
    val errorAsset: String?

) : WorkflowActionConfigurationEntity {
    override fun toWorkflowActionConfiguration(): WorkflowActionConfiguration {
        return WorkflowActionConfigurationIssueCard(errorAsset)
    }
}
