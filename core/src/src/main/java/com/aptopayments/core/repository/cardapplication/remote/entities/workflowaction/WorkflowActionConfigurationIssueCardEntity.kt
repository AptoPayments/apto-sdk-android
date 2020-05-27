package com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.core.data.workflowaction.WorkflowActionConfiguration
import com.aptopayments.core.data.workflowaction.WorkflowActionConfigurationIssueCard
import com.google.gson.annotations.SerializedName

class WorkflowActionConfigurationIssueCardEntity(

    @SerializedName("error_asset")
    var errorAsset: String?

) : WorkflowActionConfigurationEntity {
    override fun toWorkflowActionConfiguration(): WorkflowActionConfiguration {
        return WorkflowActionConfigurationIssueCard(errorAsset)
    }
}
