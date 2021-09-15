package com.aptopayments.mobile.repository.user

import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.repository.user.remote.UserService
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.junit.jupiter.api.Test

private const val CUSTODIAN_UID = "custodian"
private const val METADATA = "metadata"

class UserRepositoryTest {

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
