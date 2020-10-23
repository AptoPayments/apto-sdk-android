package com.aptopayments.mobile.platform

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.card.usecases.IssueCardUseCase
import com.aptopayments.mobile.repository.stats.usecases.GetMonthlySpendingUseCase
import com.aptopayments.mobile.repository.user.usecases.CreateUserUseCase
import com.nhaarman.mockitokotlin2.*
import org.junit.After
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mock

class AptoPlatformTest : UnitTest(), KoinTest {
    private val sut = AptoPlatform

    // Collaborators
    @Mock
    private lateinit var issueCardCardProductUseCase: IssueCardUseCase

    @Mock
    private lateinit var createUserUseCase: CreateUserUseCase

    @Mock
    private lateinit var userSessionRepository: UserSessionRepository

    @Mock
    private lateinit var useCasesWrapper: UseCasesWrapper

    override fun setUp() {
        super.setUp()
        AptoPlatform.koin = startKoin {
            modules(module {
                factory<UserSessionRepository> { userSessionRepository }
            })
        }.koin
        given { useCasesWrapper.issueCardCardProductUseCase }.willReturn(issueCardCardProductUseCase)
        given { useCasesWrapper.createUserUseCase }.willReturn(createUserUseCase)
        sut.useCasesWrapper = useCasesWrapper
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `issue card called invoke use case`() {
        // When
        AptoPlatform.issueCard(cardProductId = "card_product_id", credential = null) {}

        // Then
        verify(issueCardCardProductUseCase).invoke(TestDataProvider.anyObject(), TestDataProvider.anyObject())
    }

    @Test
    fun `additional params are sent to issue card`() {
        // Given
        val additionalFields = mapOf<String, Any>("field" to "value")
        val expectedParams = IssueCardUseCase.Params(
            cardProductId = "card_product_id",
            credential = null,
            additionalFields = additionalFields,
            initialFundingSourceId = null
        )

        // When
        AptoPlatform.issueCard(
            cardProductId = "card_product_id",
            credential = null,
            additionalFields = additionalFields
        ) {}

        // Then
        verify(issueCardCardProductUseCase).invoke(eq(expectedParams), TestDataProvider.anyObject())
    }

    @Test
    fun `initial funding source id is sent to issue card`() {
        // Given
        val initialFundingSourceId = "initial_funding_source_id"
        val expectedParams = IssueCardUseCase.Params(
            cardProductId = "card_product_id",
            credential = null,
            additionalFields = null,
            initialFundingSourceId = initialFundingSourceId
        )

        // When
        AptoPlatform.issueCard(
            cardProductId = "card_product_id",
            credential = null,
            additionalFields = null,
            initialFundingSourceId = initialFundingSourceId
        ) {}

        // Then
        verify(issueCardCardProductUseCase).invoke(eq(expectedParams), TestDataProvider.anyObject())
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
        val useCase = mock<GetMonthlySpendingUseCase>()
        whenever(useCasesWrapper.getMonthlySpendingUseCase).thenReturn(useCase)
        val params = GetMonthlySpendingUseCase.Params(cardId, "October", "2020")

        sut.cardMonthlySpending(cardId, month, year) {}

        verify(useCase).invoke(eq(params), TestDataProvider.anyObject())
    }
}
