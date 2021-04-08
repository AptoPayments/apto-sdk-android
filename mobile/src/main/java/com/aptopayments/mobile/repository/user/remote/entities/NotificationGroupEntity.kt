package com.aptopayments.mobile.repository.user.remote.entities

import com.aptopayments.mobile.data.user.notificationpreferences.NotificationGroup
import com.google.gson.annotations.SerializedName
import java.util.Locale

internal data class NotificationGroupEntity(

    @SerializedName("category_id")
    val categoryId: String = "",

    @SerializedName("group_id")
    val groupId: String = "",

    @SerializedName("state")
    val state: String = "",

    @SerializedName("active_channels")
    val activeChannels: ActiveChannelsEntity? = null

) {
    fun toNotificationGroup() = NotificationGroup(
        categoryId = parseCategoryId(categoryId),
        groupId = parseGroupId(groupId),
        state = parseState(state),
        activeChannels = activeChannels?.toActiveChannels()
    )

    private fun parseCategoryId(categoryId: String): NotificationGroup.Category? {
        return try {
            NotificationGroup.Category.valueOf(categoryId.toUpperCase(Locale.US))
        } catch (exception: IllegalArgumentException) {
            null
        }
    }

    private fun parseGroupId(groupId: String): NotificationGroup.Group? {
        return try {
            NotificationGroup.Group.valueOf(groupId.toUpperCase(Locale.US))
        } catch (exception: IllegalArgumentException) {
            null
        }
    }

    private fun parseState(state: String): NotificationGroup.State? {
        return try {
            NotificationGroup.State.valueOf(state.toUpperCase(Locale.US))
        } catch (exception: IllegalArgumentException) {
            null
        }
    }
}
