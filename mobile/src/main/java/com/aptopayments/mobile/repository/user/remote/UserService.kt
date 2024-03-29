package com.aptopayments.mobile.repository.user.remote

import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.data.user.agreements.AgreementAction
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.user.remote.entities.NotificationPreferencesEntity
import com.aptopayments.mobile.repository.user.remote.entities.UserEntity
import com.aptopayments.mobile.repository.user.remote.requests.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.URLEncoder

private const val UTF8 = "UTF-8"

internal class UserService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val userApi by lazy { apiCatalog.api().create(UserApi::class.java) }

    fun createUser(
        userData: DataPointList,
        custodianUid: String?,
        metadata: String?
    ): Either<Failure, User> {
        val request = CreateUserDataRequest.from(userData, custodianUid, metadata)
        return request(userApi.createUserSigned(request), { it.toUser() }, UserEntity())
    }

    fun createUser(webToken: String) = request(
        userApi.createUser(webToken.toRequestBody("text/plain".toMediaTypeOrNull())), { it.toUser() }, UserEntity()
    )

    fun updateUser(userData: DataPointList) =
        request(userApi.updateUser(UserDataRequest.from(userData)), { it.toUser() }, UserEntity())

    fun loginUser(loginUserRequest: LoginUserRequest) =
        request(userApi.loginExistingUser(loginUserRequest), { it.toUser() }, UserEntity())

    fun registerPushDevice(pushDeviceRequest: PushDeviceRequest) =
        request(userApi.registerPushDevice(pushDeviceRequest), { }, Unit)

    fun unregisterPushDevice(userToken: String, pushToken: String) =
        request(
            userApi.unregisterPushDevice(
                userToken = authorizationHeader(userToken),
                pushToken = URLEncoder.encode(pushToken, UTF8)
            ),
            { }, Unit
        )

    fun getNotificationPreferences() =
        request(
            userApi.getNotificationPreferences(),
            { it.toNotificationPreferences() },
            NotificationPreferencesEntity()
        )

    fun updateNotificationPreferences(notificationPreferencesRequest: NotificationPreferencesRequest) =
        request(
            userApi.updateNotificationPreferences(request = notificationPreferencesRequest),
            { it.toNotificationPreferences() }, NotificationPreferencesEntity()
        )

    fun reviewAgreements(keys: List<String>, action: AgreementAction) =
        request(
            userApi.reviewAgreements(request = AgreementsInputRequest(keys, action.toString())),
            { }, Unit
        )

    private fun authorizationHeader(userToken: String) = "Bearer $userToken"
}
