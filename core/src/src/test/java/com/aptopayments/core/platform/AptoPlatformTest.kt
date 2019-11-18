package com.aptopayments.core.platform

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.repository.UserSessionRepository
import com.aptopayments.core.repository.card.usecases.IssueCardUseCase
import com.aptopayments.core.repository.user.usecases.CreateUserUseCase
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.mockito.Mock

class AptoPlatformTest : UnitTest() {
    private val sut = AptoPlatform

    // Collaborators
    @Mock private lateinit var issueCardCardProductUseCase: IssueCardUseCase
    @Mock private lateinit var createUserUseCase: CreateUserUseCase
    @Mock private lateinit var userSessionRepository: UserSessionRepository
    @Mock private lateinit var useCasesWrapper: UseCasesWrapper

    override fun setUp() {
        super.setUp()
        given { useCasesWrapper.issueCardCardProductUseCase }.willReturn(issueCardCardProductUseCase)
        given { useCasesWrapper.createUserUseCase }.willReturn(createUserUseCase)
        given { useCasesWrapper.userSessionRepository }.willReturn(userSessionRepository)
        sut.useCasesWrapper = useCasesWrapper
    }

    @Test
    fun `issue card called invoke use case`() {
        // When
        AptoPlatform.issueCard(
                cardProductId = "card_product_id",
                credential = null
        ) {}

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
}