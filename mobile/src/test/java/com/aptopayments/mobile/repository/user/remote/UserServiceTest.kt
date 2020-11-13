package com.aptopayments.mobile.repository.user.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.user.PhoneDataPoint
import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.data.user.VerificationStatus
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.repository.user.remote.requests.LoginUserRequest
import com.aptopayments.mobile.repository.user.remote.requests.NotificationPreferencesRequest
import com.aptopayments.mobile.repository.user.remote.requests.PushDeviceRequest
import com.aptopayments.mobile.repository.verification.remote.entities.VerificationEntity
import org.junit.Test
import org.koin.core.inject

private const val CUSTODIAN_UID = "id_12345"
private const val METADATA = "METADATA"
private const val TOKEN = "token_1234"
private const val PUSH_TOKEN = "push_token_12345"

internal class UserServiceTest : NetworkServiceTest() {

    private val sut: UserService by inject()

    @Test
    fun `when createUser then request is made to the correct url`() {
        enqueueFile("createUserResponse200.json")

        sut.createUser(DataPointList(emptyList()), CUSTODIAN_UID, METADATA)

        assertRequestSentTo("v1/user", "POST")
    }

    @Test
    fun `when startCardApplication then request is made correctly`() {
        enqueueFile("createUserResponse200.json")

        sut.createUser(
            generateCreateUserDataPointList(),
            CUSTODIAN_UID,
            METADATA
        )

        assertRequestBodyFile("createUserRequest.json")
    }

    @Test
    fun `when updateUser then request is made to the correct url`() {
        enqueueContent("{}")

        sut.updateUser(DataPointList())

        assertRequestSentTo("v1/user", "PUT")
    }

    @Test
    fun `when loginUser then request is made to the correct url`() {
        enqueueContent("{}")

        sut.loginUser(LoginUserRequest(ListEntity<VerificationEntity>()))

        assertRequestSentTo("v1/user/login", "POST")
    }
    @Test
    fun `when registerPushDevice then request is made to the correct url`() {
        enqueueContent("{}")

        sut.registerPushDevice(PushDeviceRequest(pushToken = PUSH_TOKEN))

        assertRequestSentTo("v1/user/pushdevice", "POST")
    }

    @Test
    fun `when unregisterPushDevice then request is made to the correct url`() {
        enqueueContent("{}")

        sut.unregisterPushDevice(TOKEN, PUSH_TOKEN)

        assertRequestSentTo("v1/user/pushdevice/$PUSH_TOKEN", "DELETE")
    }
    @Test
    fun `when getNotificationPreferences then request is made to the correct url`() {
        enqueueContent("{}")

        sut.getNotificationPreferences()

        assertRequestSentTo("v1/user/notifications/preferences", "GET")
    }
    @Test
    fun `when updateNotificationPreferences then request is made to the correct url`() {
        enqueueContent("{}")

        sut.updateNotificationPreferences(NotificationPreferencesRequest(emptyList()))

        assertRequestSentTo("v1/user/notifications/preferences", "PUT")
    }

    private fun generateCreateUserDataPointList(): DataPointList {
        val dataPoint = PhoneDataPoint(
            verified = false,
            verification = Verification(
                secondaryCredential = null,
                status = VerificationStatus.PASSED,
                verificationId = "entity_123",
                verificationType = "phone"
            ),
            notSpecified = false,
            phoneNumber = PhoneNumber("44", "7709000000")
        )
        return DataPointList(listOf(dataPoint))
    }
}
