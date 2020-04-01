package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.workflowaction.WorkflowActionCollectUserData
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.repository.cardapplication.remote.entities.workflowaction.WorkflowActionConfigurationEntity
import com.google.gson.annotations.SerializedName

internal class CollectUserDataActionConfigurationEntity : WorkflowActionConfigurationEntity {

    @SerializedName("required_datapoint_groups")
    val requiredDataPointsGroups: ListEntity<DataPointGroupEntity> = ListEntity()

    override fun toWorkflowActionConfiguration(): WorkflowActionCollectUserData {
        val dataPoints = requiredDataPointsGroups.data?.sortedBy { it.order }
            ?.flatMap { group -> group.datapoints.data?.map { it.toRequiredDataPoint() } ?: listOf() }
            ?.filter { it.type != null } ?: listOf()

        return WorkflowActionCollectUserData(dataPoints)
    }
}
