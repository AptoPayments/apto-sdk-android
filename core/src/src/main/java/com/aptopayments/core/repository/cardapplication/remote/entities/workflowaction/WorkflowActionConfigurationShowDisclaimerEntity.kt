package com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.core.data.workflowaction.WorkflowActionConfigurationShowDisclaimer
import com.google.gson.annotations.SerializedName

class WorkflowActionConfigurationShowDisclaimerEntity(

    @SerializedName("disclaimer")
    var contentEntity: ContentEntity

) : WorkflowActionConfigurationEntity {
    override fun toWorkflowActionConfiguration() = WorkflowActionConfigurationShowDisclaimer(
        content = contentEntity.toContent()
    )
}
