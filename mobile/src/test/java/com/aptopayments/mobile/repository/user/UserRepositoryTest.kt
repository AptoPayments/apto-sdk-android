package com.aptopayments.mobile.repository.user

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.remote.UserService
import com.aptopayments.mobile.repository.user.remote.entities.UserEntity
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class UserRepositoryTest : UnitTest() {

    private lateinit var repositoryNetwork: UserRepository.Network

    @Mock
    private lateinit var networkHandler: NetworkHandler
    @Mock
    private lateinit var service: UserService
    @Mock
    private lateinit var createUserRequest: Call<UserEntity>
    @Mock
    private lateinit var createUserResponse: Response<UserEntity>

    @Before
    override fun setUp() {
        super.setUp()
        startKoin {
            modules(module {
                single { networkHandler }
                single { service }
            })
        }
        repositoryNetwork = UserRepository.Network(networkHandler, service)
    }

    @Test
    fun `start the user creation service without custodian uid`() {
        // Given
        val userData = DataPointList()
        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(service.createUser(userData, null)).thenReturn(createUserRequest)
        whenever(createUserResponse.isSuccessful).thenReturn(true)
        whenever(createUserRequest.execute()).thenReturn(createUserResponse)

        // When
        repositoryNetwork.createUser(userData, null)

        // Then
        verify(service).createUser(userData, null)
    }

    @Test
    fun `pass custodian uid to user creation service`() {
        // Given
        val userData = DataPointList()
        val custodianUid = "custodian_uid"
        whenever(networkHandler.isConnected).thenReturn(true)
        whenever(service.createUser(userData, custodianUid)).thenReturn(createUserRequest)
        whenever(createUserResponse.isSuccessful).thenReturn(true)
        whenever(createUserRequest.execute()).thenReturn(createUserResponse)

        // When
        repositoryNetwork.createUser(userData, custodianUid)

        // Then
        verify(service).createUser(userData, custodianUid)
    }
}
