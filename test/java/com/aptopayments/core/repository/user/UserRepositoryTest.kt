package com.aptopayments.core.repository.user

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.remote.UserService
import com.aptopayments.core.repository.user.remote.entities.UserEntity
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import retrofit2.Call
import retrofit2.Response

class UserRepositoryTest : UnitTest() {

    private lateinit var repositoryNetwork: UserRepository.Network

    @Mock private lateinit var networkHandler: NetworkHandler
    @Mock private lateinit var service: UserService
    @Mock private lateinit var createUserRequest: Call<UserEntity>
    @Mock private lateinit var createUserResponse: Response<UserEntity>

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
        Mockito.`when`(networkHandler.isConnected).thenReturn(true)
        Mockito.`when`(service.createUser(userData, null)).thenReturn(createUserRequest)
        Mockito.`when`(createUserResponse.isSuccessful).thenReturn(true)
        Mockito.`when`(createUserRequest.execute()).thenReturn(createUserResponse)

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
        Mockito.`when`(networkHandler.isConnected).thenReturn(true)
        Mockito.`when`(service.createUser(userData, custodianUid)).thenReturn(createUserRequest)
        Mockito.`when`(createUserResponse.isSuccessful).thenReturn(true)
        Mockito.`when`(createUserRequest.execute()).thenReturn(createUserResponse)

        // When
        repositoryNetwork.createUser(userData, custodianUid)

        // Then
        verify(service).createUser(userData, custodianUid)
    }
}
