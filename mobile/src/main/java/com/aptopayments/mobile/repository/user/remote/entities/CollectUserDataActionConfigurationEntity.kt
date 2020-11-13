package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.data.workflowaction.WorkflowActionConfigurationCollectUserData
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationEntity
import com.google.gson.annotations.SerializedName

internal class CollectUserDataActionConfigurationEntity : WorkflowActionConfigurationEntity {

    @SerializedName("required_datapoint_groups")
    val requiredDataPointsGroups: ListEntity<DataPointGroupEntity> = ListEntity()

    override fun toWorkflowActionConfiguration(): WorkflowActionConfigurationCollectUserData {
        val dataPoints = requiredDataPointsGroups.data?.sortedBy { it.order }
            ?.flatMap { group -> group.datapoints.data?.map { it.toRequiredDataPoint() } ?: listOf() }
            ?.filter { it.type != null } ?: listOf()

        return WorkflowActionConfigurationCollectUserData(dataPoints)
    }
}
