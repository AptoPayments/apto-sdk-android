package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.exception.Failure.ServerError
import com.aptopayments.mobile.extension.shouldBeLeftAndInstanceOf
import com.aptopayments.mobile.extension.shouldBeRightAndInstanceOf
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.UserRepository
import com.aptopayments.mobile.repository.user.usecases.CreateUserUseCase.Params
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.willReturn
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CreateUserUseCaseTest : UnitTest() {
    private lateinit var sut: CreateUserUseCase

    // Collaborators
    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var networkHandler: NetworkHandler
    private val params = Params(userData = DataPointList(), custodianUid = "custodianUid")

    @Before
    override fun setUp() {
        super.setUp()
        sut = CreateUserUseCase(userRepository, networkHandler)
    }

    @Test
    fun `create user call repository with the appropriate parameters`() {
        // When
        sut.run(params)

        // Then
        verify(userRepository).createUser(params.userData, params.custodianUid)
    }

    @Test
    fun `repository return success sut return success`() {
        // Given
        given { userRepository.createUser(params.userData, params.custodianUid) }
            .willReturn { Either.Right(TestDataProvider.provideUser(params.userData)) }

        // When
        val result = sut.run(params)

        // Then
        result.shouldBeRightAndInstanceOf(User::class.java)
    }

    @Test
    fun `repository return failure sut return failure`() {
        // Given
        given { userRepository.createUser(params.userData, params.custodianUid) }
            .willReturn { Either.Left(ServerError(code = null)) }

        // When
        val result = sut.run(params)

        // Then
        result.shouldBeLeftAndInstanceOf(ServerError::class.java)
    }
}
