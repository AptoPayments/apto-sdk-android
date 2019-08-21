package com.aptopayments.core.repository.user.usecases

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.user.User
import com.aptopayments.core.exception.Failure.ServerError
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository
import com.aptopayments.core.repository.user.usecases.CreateUserUseCase.Params
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.willReturn
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class CreateUserUseCaseTest : UnitTest() {
    private lateinit var sut: CreateUserUseCase

    // Collaborators
    @Mock private lateinit var userRepository: UserRepository
    @Mock private lateinit var networkHandler: NetworkHandler
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
        result shouldBeInstanceOf Either.Right::class.java
        result.isRight shouldEqual true
        result.either({}, { user -> user shouldBeInstanceOf User::class.java})
    }

    @Test
    fun `repository return failure sut return failure`() {
        // Given
        given { userRepository.createUser(params.userData, params.custodianUid) }
                .willReturn { Either.Left(ServerError(errorCode = null)) }

        // When
        val result = sut.run(params)

        // Then
        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf ServerError::class.java }, {})
    }
}
