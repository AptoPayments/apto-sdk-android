package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.workflowaction.WorkflowActionConfiguration

internal interface WorkflowActionConfigurationEntity {
    fun toWorkflowActionConfiguration(): WorkflowActionConfiguration
}
