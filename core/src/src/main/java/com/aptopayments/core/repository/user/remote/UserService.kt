package com.aptopayments.core.repository.user.remote

import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.user.remote.entities.NotificationPreferencesEntity
import com.aptopayments.core.repository.user.remote.entities.UserEntity
import com.aptopayments.core.repository.user.remote.requests.*
import retrofit2.Call
import java.net.URLEncoder

private const val CHARSET = "UTF_8"

internal class UserService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val userApi by lazy { apiCatalog.api().create(UserApi::class.java) }

    fun createUser(userData: DataPointList, custodianUid: String?): Call<UserEntity> {
        val request = CreateUserDataRequest.from(userData, custodianUid)
        return userApi.createUser(request = request)
    }

    fun updateUser(userData: DataPointList): Call<UserEntity> {
        val request = UserDataRequest.from(userData)
        return userApi.updateUser(request = request)
    }

    fun loginUser(loginUserRequest: LoginUserRequest): Call<UserEntity> =
        userApi.loginExistingUser(request = loginUserRequest)

    fun registerPushDevice(pushDeviceRequest: PushDeviceRequest): Call<Unit> =
        userApi.registerPushDevice(request = pushDeviceRequest)

    fun unregisterPushDevice(userToken: String, pushToken: String): Call<Unit> =
        userApi.unregisterPushDevice(
            userToken = authorizationHeader(userToken),
            pushToken = URLEncoder.encode(pushToken, CHARSET)
        )

    fun getNotificationPreferences(): Call<NotificationPreferencesEntity> = userApi.getNotificationPreferences()

    fun updateNotificationPreferences(notificationPreferencesRequest: NotificationPreferencesRequest): Call<Unit> =
        userApi.updateNotificationPreferences(request = notificationPreferencesRequest)

    private fun authorizationHeader(userToken: String) = "Bearer $userToken"
}
