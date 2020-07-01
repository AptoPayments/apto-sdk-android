package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.workflowaction.WorkflowActionConfigurationShowDisclaimer
import com.google.gson.annotations.SerializedName

internal class WorkflowActionConfigurationShowDisclaimerEntity(

    @SerializedName("disclaimer")
    var contentEntity: ContentEntity

) : WorkflowActionConfigurationEntity {
    override fun toWorkflowActionConfiguration() = WorkflowActionConfigurationShowDisclaimer(
        content = contentEntity.toContent()
    )
}
