package com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.core.data.workflowaction.WorkflowActionConfiguration
import com.aptopayments.core.data.workflowaction.WorkflowActionConfigurationSelectBalanceStore
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.repository.oauth.remote.entities.AllowedBalanceTypeEntity
import com.google.gson.annotations.SerializedName

internal class WorkflowActionConfigurationSelectBalanceStoreEntity (

        @SerializedName("allowed_balance_types")
        var allowedBalanceTypes: ListEntity<AllowedBalanceTypeEntity>

) : WorkflowActionConfigurationEntity {
    override fun toWorkflowActionConfiguration(): WorkflowActionConfiguration {
        val parsedAllowedBalanceTypes = allowedBalanceTypes.data?.let {
            it.map { allowedBalanceType -> allowedBalanceType.toAllowedBalanceType() }
        }
        return WorkflowActionConfigurationSelectBalanceStore(parsedAllowedBalanceTypes)
    }
}
