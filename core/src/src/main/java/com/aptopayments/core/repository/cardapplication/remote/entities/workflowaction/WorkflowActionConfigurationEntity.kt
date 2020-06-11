package com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.core.data.workflowaction.WorkflowActionConfiguration

internal interface WorkflowActionConfigurationEntity {
    fun toWorkflowActionConfiguration(): WorkflowActionConfiguration
}
