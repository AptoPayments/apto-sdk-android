package com.aptopayments.mobile.repository.user.remote

import com.aptopayments.mobile.network.X_AUTHORIZATION
import com.aptopayments.mobile.repository.user.remote.entities.NotificationPreferencesEntity
import com.aptopayments.mobile.repository.user.remote.entities.UserEntity
import com.aptopayments.mobile.repository.user.remote.requests.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

private const val CREATE_USER_PATH = "/v1/user"
private const val USER_PATH = "v1/user"
private const val LOGIN_USER_PATH = "/v1/user/login"
private const val REGISTER_PUSH_DEVICE_PATH = "/v1/user/pushdevice"
private const val UNREGISTER_PUSH_DEVICE_PATH = "/v1/user/pushdevice/{push_token}"
private const val NOTIFICATION_PREFERENCES_PATH = "/v1/user/notifications/preferences"
private const val AGREEMENTS_PATH = "/v1/agreements"

private const val PUSH_TOKEN = "push_token"

internal interface UserApi {

    @POST(CREATE_USER_PATH)
    fun createUserSigned(@Body request: CreateUserDataRequest): Call<UserEntity>

    @POST(CREATE_USER_PATH)
    @Headers("Content-Type: application/jwt")
    fun createUser(@Body webToken: RequestBody? = null): Call<UserEntity>

    @PUT(USER_PATH)
    fun updateUser(@Body request: UserDataRequest): Call<UserEntity>

    @POST(LOGIN_USER_PATH)
    fun loginExistingUser(@Body request: LoginUserRequest): Call<UserEntity>

    @POST(REGISTER_PUSH_DEVICE_PATH)
    fun registerPushDevice(@Body request: PushDeviceRequest): Call<Unit>

    @DELETE(UNREGISTER_PUSH_DEVICE_PATH)
    fun unregisterPushDevice(
        @Header(X_AUTHORIZATION) userToken: String,
        @Path(PUSH_TOKEN, encoded = true) pushToken: String
    ): Call<Unit>

    @GET(NOTIFICATION_PREFERENCES_PATH)
    fun getNotificationPreferences(): Call<NotificationPreferencesEntity>

    @PUT(NOTIFICATION_PREFERENCES_PATH)
    fun updateNotificationPreferences(@Body request: NotificationPreferencesRequest):
        Call<NotificationPreferencesEntity>

    @POST(AGREEMENTS_PATH)
    fun reviewAgreements(@Body request: AgreementsInputRequest): Call<Unit>
}
