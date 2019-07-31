package com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.core.data.workflowaction.WorkflowActionConfiguration

interface WorkflowActionConfigurationEntity {
    fun toWorkflowActionConfiguration(): WorkflowActionConfiguration
}
