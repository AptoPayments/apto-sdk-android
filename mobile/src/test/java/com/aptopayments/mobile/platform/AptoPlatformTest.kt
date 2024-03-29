package com.aptopayments.mobile.platform

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.OrderPhysicalCardConfig
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.card.usecases.GetOrderPhysicalCardConfigurationUseCase
import com.aptopayments.mobile.repository.card.usecases.OrderPhysicalCardUseCase
import com.aptopayments.mobile.repository.stats.usecases.GetMonthlySpendingUseCase
import com.aptopayments.mobile.repository.user.usecases.CreateUserUseCase
import org.mockito.kotlin.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

private const val CARD_ID = "cardId"

@Suppress("UNCHECKED_CAST")
class AptoPlatformTest : UnitTest() {
    private val sut = AptoPlatform

    // Collaborators
    private val createUserUseCase: CreateUserUseCase = mock()
    private val userSessionRepository: UserSessionRepository = mock()
    private val getMonthlySpendingUseCase = mock<GetMonthlySpendingUseCase>()

    private val getOrderPhysicalCardConfigurationUseCase: GetOrderPhysicalCardConfigurationUseCase = mock()
    private val orderPhysicalCardUseCase: OrderPhysicalCardUseCase = mock()

    @BeforeEach
    fun setUp() {
        AptoPlatform.koin = startKoin {
            modules(
                module {
                    factory<UserSessionRepository> { userSessionRepository }
                    factory { createUserUseCase }
                    factory { getMonthlySpendingUseCase }
                    factory { orderPhysicalCardUseCase }
                    factory { getOrderPhysicalCardConfigurationUseCase }
                }
            )
        }.koin
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `create user call use case`() {
        // Given
        val expectedParams = CreateUserUseCase.Params(DataPointList(), "custodian_uid")

        // When
        sut.createUser(expectedParams.userData, "custodian_uid") {}

        // Then
        verify(createUserUseCase).invoke(eq(expectedParams), TestDataProvider.anyObject())
    }

    @Test
    fun `create user without custodian uid pass null`() {
        // Given
        val expectedParams = CreateUserUseCase.Params(DataPointList(), null)

        // When
        sut.createUser(expectedParams.userData) {}

        // Then
        verify(createUserUseCase).invoke(eq(expectedParams), TestDataProvider.anyObject())
    }

    @Test
    fun `set user token call user repository`() {
        // Given
        val userToken = "user_token"

        // When
        sut.setUserToken(userToken)

        // Then
        verify(userSessionRepository).userToken = userToken
    }

    @Test
    fun `cardMonthlySpending calls correctly to UseCase`() {
        val cardId = "cardId"
        val month = 10
        val year = 2020
        val params = GetMonthlySpendingUseCase.Params(cardId, "October", "2020")

        sut.cardMonthlySpending(cardId, month, year) {}

        verify(getMonthlySpendingUseCase).invoke(eq(params), TestDataProvider.anyObject())
    }

    @Test
    fun `getOrderPhysicalCardConfiguration calls correctly the UseCase`() {
        val config = TestDataProvider.provideOrderPhysicalCardConfig()
        val params = GetOrderPhysicalCardConfigurationUseCase.Params(CARD_ID)
        whenever(getOrderPhysicalCardConfigurationUseCase.invoke(eq(params), TestDataProvider.anyObject())).thenAnswer { invocation ->
            (invocation.arguments[1] as (Either<Failure, OrderPhysicalCardConfig>) -> Unit).invoke(config.right())
        }
        val callback: (Either<Failure, OrderPhysicalCardConfig>) -> Unit = mock()

        sut.getOrderPhysicalCardConfig(CARD_ID, callback)

        verify(getOrderPhysicalCardConfigurationUseCase).invoke(eq(params), TestDataProvider.anyObject())
        verify(callback).invoke(eq(config.right()))
    }

    @Test
    fun `orderPhysicalCardUseCase calls correctly the UseCase`() {
        val card = TestDataProvider.provideCard(accountID = CARD_ID)
        val params = OrderPhysicalCardUseCase.Params(CARD_ID)
        whenever(orderPhysicalCardUseCase.invoke(eq(params), TestDataProvider.anyObject())).thenAnswer { invocation ->
            (invocation.arguments[1] as (Either<Failure, Card>) -> Unit).invoke(card.right())
        }
        val callback: (Either<Failure, Card>) -> Unit = mock()

        sut.orderPhysicalCard(CARD_ID, callback)

        verify(orderPhysicalCardUseCase).invoke(eq(params), TestDataProvider.anyObject())
        verify(callback).invoke(eq(card.right()))
    }
}
