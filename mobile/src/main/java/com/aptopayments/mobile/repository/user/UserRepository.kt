package com.aptopayments.mobile.repository.user

import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.data.user.agreements.ReviewAgreementsInput
import com.aptopayments.mobile.data.user.notificationpreferences.NotificationGroup
import com.aptopayments.mobile.data.user.notificationpreferences.NotificationPreferences
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.user.remote.UserService
import com.aptopayments.mobile.repository.user.remote.requests.LoginUserRequest
import com.aptopayments.mobile.repository.user.remote.requests.NotificationPreferencesRequest
import com.aptopayments.mobile.repository.user.remote.requests.PushDeviceRequest
import com.aptopayments.mobile.repository.user.usecases.UnregisterPushDeviceParams

internal interface UserRepository : BaseNoNetworkRepository {

    fun createUser(userData: DataPointList, custodianUid: String?, metadata: String?): Either<Failure, User>
    fun updateUserData(userData: DataPointList): Either<Failure, User>
    fun loginUser(verificationList: List<Verification>): Either<Failure, User>
    fun registerPushDevice(pushToken: String): Either<Failure, Unit>
    fun unregisterPushDevice(params: UnregisterPushDeviceParams): Either<Failure, Unit>
    fun getNotificationPreferences(): Either<Failure, NotificationPreferences>
    fun updateNotificationPreferences(notificationPreferencesList: List<NotificationGroup>): Either<Failure, NotificationPreferences>
    fun reviewAgreements(input: ReviewAgreementsInput): Either<Failure, Unit>
}

internal class UserRepositoryImpl(
    private val service: UserService
) : UserRepository {

    override fun createUser(
        userData: DataPointList,
        custodianUid: String?,
        metadata: String?
    ) = service.createUser(userData, custodianUid, metadata)

    override fun updateUserData(userData: DataPointList) = service.updateUser(userData)

    override fun loginUser(verificationList: List<Verification>) =
        service.loginUser(LoginUserRequest.from(verificationList))

    override fun registerPushDevice(pushToken: String) =
        service.registerPushDevice(PushDeviceRequest(pushToken = pushToken))

    override fun unregisterPushDevice(params: UnregisterPushDeviceParams) =
        service.unregisterPushDevice(params.userToken, params.pushToken)

    override fun getNotificationPreferences() =
        service.getNotificationPreferences()

    override fun updateNotificationPreferences(notificationPreferencesList: List<NotificationGroup>) =
        service.updateNotificationPreferences(
            NotificationPreferencesRequest.from(notificationPreferencesList)
        )

    override fun reviewAgreements(input: ReviewAgreementsInput) = service.reviewAgreements(input.keys, input.action)
}
