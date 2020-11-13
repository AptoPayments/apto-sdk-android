package com.aptopayments.mobile.repository.user

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.repository.user.remote.UserService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

private const val CUSTODIAN_UID = "custodian"
private const val METADATA = "metadata"

class UserRepositoryTest : UnitTest() {

    private val service: UserService = mock()

    private val sut = UserRepositoryImpl(service)

    @Test
    fun `whenever createUser with no extra parameters then correct call to service is made`() {
        // Given
        val userData = DataPointList()

        // When
        sut.createUser(userData, null, null)

        // Then
        verify(service).createUser(userData, null, null)
    }

    @Test
    fun `whenever createUser with custodianUid then correct call to service is made`() {
        // Given
        val userData = DataPointList()

        // When
        sut.createUser(userData, CUSTODIAN_UID, null)

        // Then
        verify(service).createUser(userData, CUSTODIAN_UID, null)
    }

    @Test
    fun `whenever createUser with metadata then correct call to service is made`() {
        // Given
        val userData = DataPointList()

        // When
        sut.createUser(userData, null, METADATA)

        // Then
        verify(service).createUser(userData, null, METADATA)
    }
}
