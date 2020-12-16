package com.aptopayments.mobile.data.workflowaction

data class WorkflowActionConfigurationSelectBalanceStore(
    val allowedBalanceTypes: List<AllowedBalanceType>?,
    val assetUrl: String?
) : WorkflowActionConfiguration
