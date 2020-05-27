package com.aptopayments.core.data.workflowaction

import com.aptopayments.core.data.content.Content

data class WorkflowActionConfigurationShowDisclaimer(
    var content: Content
) : WorkflowActionConfiguration
