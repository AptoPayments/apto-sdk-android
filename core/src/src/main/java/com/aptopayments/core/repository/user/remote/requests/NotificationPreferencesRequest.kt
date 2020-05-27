package com.aptopayments.core.repository.user.remote.requests

import com.aptopayments.core.data.user.notificationpreferences.NotificationGroup
import com.aptopayments.core.repository.user.remote.entities.ActiveChannelsEntity
import com.aptopayments.core.repository.user.remote.entities.NotificationGroupEntity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class NotificationPreferencesRequest(

    @SerializedName("preferences")
    val preferences: List<NotificationGroupEntity>

) : Serializable {
    companion object {
        fun from(notificationGroupList: List<NotificationGroup>): NotificationPreferencesRequest {
            val notificationGroupEntityList: ArrayList<NotificationGroupEntity> = ArrayList()
            for (notification in notificationGroupList) {
                notificationGroupEntityList.add(
                    NotificationGroupEntity(
                        groupId = notification.groupId.toString(),
                        activeChannels = notification.activeChannels?.let { ActiveChannelsEntity.from(it) })
                )
            }

            return NotificationPreferencesRequest(notificationGroupEntityList)
        }
    }
}
