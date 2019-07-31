package com.aptopayments.core.repository.user.remote.entities

import com.aptopayments.core.data.user.notificationpreferences.NotificationGroup
import com.google.gson.annotations.SerializedName

internal data class NotificationGroupEntity (

        @SerializedName("category_id")
        val categoryId: String = "",

        @SerializedName("group_id")
        val groupId: String = "",

        @SerializedName("state")
        val state: String = "",

        @SerializedName("active_channels")
        val activeChannels: ActiveChannelsEntity? = null

) {
    fun toNotificationGroup() = NotificationGroup (
            categoryId = parseCategoryId(categoryId),
            groupId = parseGroupId(groupId),
            state = parseState(state),
            activeChannels = activeChannels?.toActiveChannels()
    )

    private fun parseCategoryId(categoryId: String): NotificationGroup.Category? {
        return try {
            NotificationGroup.Category.valueOf(categoryId.toUpperCase())
        } catch (exception: Throwable) {
            null
        }
    }

    private fun parseGroupId(groupId: String): NotificationGroup.Group? {
        return try {
            NotificationGroup.Group.valueOf(groupId.toUpperCase())
        } catch (exception: Throwable) {
            null
        }
    }

    private fun parseState(state: String): NotificationGroup.State? {
        return try {
            NotificationGroup.State.valueOf(state.toUpperCase())
        } catch (exception: Throwable) {
            null
        }
    }
}
