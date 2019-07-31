package com.aptopayments.core.repository.user.remote

import com.aptopayments.core.network.X_API_KEY
import com.aptopayments.core.network.X_AUTHORIZATION
import com.aptopayments.core.repository.user.remote.entities.NotificationPreferencesEntity
import com.aptopayments.core.repository.user.remote.entities.UserEntity
import com.aptopayments.core.repository.user.remote.requests.LoginUserRequest
import com.aptopayments.core.repository.user.remote.requests.NotificationPreferencesRequest
import com.aptopayments.core.repository.user.remote.requests.PushDeviceRequest
import com.aptopayments.core.repository.user.remote.requests.UserDataRequest
import retrofit2.Call
import retrofit2.http.*

private const val CREATE_USER_PATH = "/v1/user"
private const val USER_PATH = "v1/user"
private const val LOGIN_USER_PATH = "/v1/user/login"
private const val REGISTER_PUSH_DEVICE_PATH = "/v1/user/pushdevice"
private const val UNREGISTER_PUSH_DEVICE_PATH = "/v1/user/pushdevice/{push_token}"
private const val NOTIFICATION_PREFERENCES_PATH = "/v1/user/notifications/preferences"
private const val PUSH_TOKEN = "push_token"

internal interface UserApi {

    @POST(CREATE_USER_PATH)
    fun createUser(
            @Header(X_API_KEY) apiKey: String,
            @Body request: UserDataRequest
    ): Call<UserEntity>

    @PUT(USER_PATH)
    fun updateUser(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Body request: UserDataRequest
    ): Call<UserEntity>

    @POST(LOGIN_USER_PATH)
    fun loginExistingUser(
            @Header(X_API_KEY) apiKey: String,
            @Body request: LoginUserRequest
    ): Call<UserEntity>

    @POST(REGISTER_PUSH_DEVICE_PATH)
    fun registerPushDevice(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Body request: PushDeviceRequest
    ): Call<Unit>

    @DELETE(UNREGISTER_PUSH_DEVICE_PATH)
    fun unregisterPushDevice(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Path(PUSH_TOKEN, encoded=true) pushToken: String
    ): Call<Unit>

    @GET(NOTIFICATION_PREFERENCES_PATH)
    fun getNotificationPreferences(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String
    ): Call<NotificationPreferencesEntity>

    @PUT(NOTIFICATION_PREFERENCES_PATH)
    fun updateNotificationPreferences(
            @Header(X_API_KEY) apiKey: String,
            @Header(X_AUTHORIZATION) userToken: String,
            @Body request: NotificationPreferencesRequest
    ): Call<Unit>
}
