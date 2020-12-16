package com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction

import com.aptopayments.mobile.data.workflowaction.WorkflowActionConfiguration
import com.aptopayments.mobile.data.workflowaction.WorkflowActionConfigurationSelectBalanceStore
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.oauth.remote.entities.AllowedBalanceTypeEntity
import com.google.gson.annotations.SerializedName

internal class WorkflowActionConfigurationSelectBalanceStoreEntity(

    @SerializedName("allowed_balance_types")
    val allowedBalanceTypes: ListEntity<AllowedBalanceTypeEntity>,

    @SerializedName("action_asset")
    val assetUrl: String? = null

) : WorkflowActionConfigurationEntity {
    override fun toWorkflowActionConfiguration(): WorkflowActionConfiguration {
        val parsedAllowedBalanceTypes = allowedBalanceTypes.data?.let {
            it.map { allowedBalanceType -> allowedBalanceType.toAllowedBalanceType() }
        }
        return WorkflowActionConfigurationSelectBalanceStore(parsedAllowedBalanceTypes, assetUrl)
    }
}
