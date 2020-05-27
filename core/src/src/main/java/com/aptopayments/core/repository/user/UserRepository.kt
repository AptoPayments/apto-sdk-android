package com.aptopayments.core.repository.user

import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.user.User
import com.aptopayments.core.data.user.Verification
import com.aptopayments.core.data.user.notificationpreferences.NotificationGroup
import com.aptopayments.core.data.user.notificationpreferences.NotificationPreferences
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.platform.BaseRepository
import com.aptopayments.core.repository.user.remote.UserService
import com.aptopayments.core.repository.user.remote.entities.NotificationPreferencesEntity
import com.aptopayments.core.repository.user.remote.entities.UserEntity
import com.aptopayments.core.repository.user.remote.requests.LoginUserRequest
import com.aptopayments.core.repository.user.remote.requests.NotificationPreferencesRequest
import com.aptopayments.core.repository.user.remote.requests.PushDeviceRequest
import com.aptopayments.core.repository.user.usecases.UnregisterPushDeviceParams

internal interface UserRepository : BaseRepository {

    fun createUser(userData: DataPointList, custodianUid: String?): Either<Failure, User>
    fun updateUserData(userData: DataPointList): Either<Failure, User>
    fun loginUser(verificationList: List<Verification>): Either<Failure, User>
    fun registerPushDevice(pushToken: String): Either<Failure, Unit>
    fun unregisterPushDevice(params: UnregisterPushDeviceParams): Either<Failure, Unit>
    fun getNotificationPreferences(): Either<Failure, NotificationPreferences>
    fun updateNotificationPreferences(notificationPreferencesList: List<NotificationGroup>): Either<Failure, Unit>

    class Network constructor(
        private val networkHandler: NetworkHandler,
        private val service: UserService
    ) : BaseRepository.BaseRepositoryImpl(), UserRepository {

        override fun createUser(userData: DataPointList, custodianUid: String?): Either<Failure, User> {
            return when (networkHandler.isConnected) {
                true -> request(service.createUser(userData, custodianUid), { it.toUser() }, UserEntity())
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun updateUserData(userData: DataPointList): Either<Failure, User> {
            return when (networkHandler.isConnected) {
                true -> request(service.updateUser(userData = userData), { it.toUser() }, UserEntity())
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun loginUser(verificationList: List<Verification>): Either<Failure, User> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.loginUser(LoginUserRequest.from(verificationList)),
                    { it.toUser() }, UserEntity()
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun registerPushDevice(pushToken: String): Either<Failure, Unit> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.registerPushDevice(PushDeviceRequest(pushToken = pushToken)),
                    { }, Unit
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun unregisterPushDevice(params: UnregisterPushDeviceParams): Either<Failure, Unit> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.unregisterPushDevice(params.userToken, params.pushToken),
                    { }, Unit
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun getNotificationPreferences(): Either<Failure, NotificationPreferences> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.getNotificationPreferences(),
                    { it.toNotificationPreferences() }, NotificationPreferencesEntity()
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }

        override fun updateNotificationPreferences(notificationPreferencesList: List<NotificationGroup>): Either<Failure, Unit> {
            return when (networkHandler.isConnected) {
                true -> request(
                    service.updateNotificationPreferences(
                        NotificationPreferencesRequest.from(notificationPreferencesList)
                    ),
                    { }, Unit
                )
                false -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}
