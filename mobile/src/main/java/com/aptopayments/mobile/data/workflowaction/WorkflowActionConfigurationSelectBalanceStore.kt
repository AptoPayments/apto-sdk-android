package com.aptopayments.mobile.data.workflowaction

data class WorkflowActionConfigurationSelectBalanceStore(
    var allowedBalanceTypes: List<AllowedBalanceType>?,
    val assetUrl: String?
) : WorkflowActionConfiguration
