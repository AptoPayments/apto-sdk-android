package com.aptopayments.mobile.repository.card

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.card.ActivatePhysicalCardResult
import com.aptopayments.mobile.data.card.ActivatePhysicalCardResultType
import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.exception.Failure.ServerError
import com.aptopayments.mobile.extension.shouldBeLeftAndInstanceOf
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.extension.shouldBeRightAndInstanceOf
import com.aptopayments.mobile.functional.left
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.card.local.CardBalanceLocalDao
import com.aptopayments.mobile.repository.card.local.CardLocalRepository
import com.aptopayments.mobile.repository.card.local.entities.CardBalanceLocalEntity
import com.aptopayments.mobile.repository.card.remote.CardService
import com.aptopayments.mobile.repository.card.remote.entities.CardEntity
import com.aptopayments.mobile.repository.card.remote.requests.GetCardRequest
import com.aptopayments.mobile.repository.card.remote.requests.IssueCardRequest
import com.aptopayments.mobile.repository.card.usecases.*
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test

private const val CODE = "123456"

class CardRepositoryTest : UnitTest() {

    private val cardId = TestDataProvider.provideCardId()
    private val card = TestDataProvider.provideCard(cardId)

    private lateinit var sut: CardRepositoryImpl

    private val service: CardService = mock()
    private val cardLocalRepo: CardLocalRepository = mock()
    private val cardBalanceLocalDao: CardBalanceLocalDao = mock()
    private val userSessionRepository: UserSessionRepository = mock()

    @Before
    override fun setUp() {
        super.setUp()
        sut = CardRepositoryImpl(
            service,
            cardLocalRepo,
            cardBalanceLocalDao,
            userSessionRepository
        )
    }

    @Test
    fun `issue card with network connection call service`() {
        // Given
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        sut.issueCard(
            cardProductId = "cardProductId", credential = null, additionalFields = null,
            initialFundingSourceId = null
        )

        // Then
        verify(service).issueCard(TestDataProvider.anyObject())
    }

    @Test
    fun `issue card with additional fields pass additional fields`() {
        // Given
        val nestedMap = mapOf<String, Any>("nestedField1" to "nestedValue1")
        val additionalFields = mapOf("field1" to "value1", "field2" to 2, "nested" to nestedMap)
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        sut.issueCard(
            cardProductId = "cardProductId", credential = null,
            additionalFields = additionalFields, initialFundingSourceId = null
        )

        // Then
        verify(service).issueCard(
            IssueCardRequest(
                cardProductId = "cardProductId",
                oAuthCredentialRequest = null,
                additionalFields = additionalFields
            )
        )
    }

    @Test
    fun `issue card with initial funding source id pass initial funding source id`() {

        // Given
        val initialFundingSourceId = "initial_funding_source_id"
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        sut.issueCard(
            cardProductId = "cardProductId", credential = null, additionalFields = null,
            initialFundingSourceId = initialFundingSourceId
        )

        // Then
        verify(service).issueCard(
            IssueCardRequest(
                cardProductId = "cardProductId",
                oAuthCredentialRequest = null,
                additionalFields = null,
                initialFundingSourceId = initialFundingSourceId
            )
        )
    }

    @Test
    fun `issue card with network connection and request succeed return Right`() {
        // Given
        givenSetUpIssueCardMocks(willRequestSucceed = true)

        // When
        val result = sut.issueCard(
            cardProductId = "cardProductId",
            credential = null, additionalFields = null, initialFundingSourceId = null
        )

        // Then
        result.shouldBeRightAndInstanceOf(Card::class.java)
    }

    @Test
    fun `issue card with network connection and request fails return Left`() {
        // Given
        givenSetUpIssueCardMocks(willRequestSucceed = false)

        // When
        val result = sut.issueCard(
            cardProductId = "cardProductId",
            credential = null, additionalFields = null, initialFundingSourceId = null
        )

        // Then
        result.shouldBeLeftAndInstanceOf(ServerError::class.java)
    }

    // Helpers
    private fun givenSetUpIssueCardMocks(willRequestSucceed: Boolean) {
        if (willRequestSucceed) {
            given { service.issueCard(TestDataProvider.anyObject()) }.willReturn(CardEntity().toCard().right())
        } else {
            given { service.issueCard(TestDataProvider.anyObject()) }.willReturn(ServerError(1).left())
        }
    }

    @Test
    fun `given CardLocalRepo has data and refresh is false when getCard then service doesn't get called`() {
        whenever(cardLocalRepo.getCard(cardId)).thenReturn(TestDataProvider.provideCard(accountID = cardId))

        sut.getCard(GetCardParams(cardId = cardId, refresh = false))

        verifyZeroInteractions(service)
    }

    @Test
    fun `given CardLocalRepo has data and refresh is false when getCard then response is correct`() {
        val card = TestDataProvider.provideCard(accountID = cardId)
        whenever(cardLocalRepo.getCard(cardId)).thenReturn(card)

        val result = sut.getCard(GetCardParams(cardId = cardId, refresh = false))

        result.shouldBeRightAndEqualTo(card)
    }

    @Test
    fun `given CardLocalRepo is empty and refresh is false when getCard then service gets called`() {
        val card = TestDataProvider.provideCard(accountID = cardId)
        whenever(cardLocalRepo.getCard(cardId)).thenReturn(null)
        whenever(service.getCard(any())).thenReturn(card.right())

        val result = sut.getCard(GetCardParams(cardId = cardId, refresh = false))

        verify(service).getCard(GetCardRequest(cardId))
        result.shouldBeRightAndEqualTo(card)
    }

    @Test
    fun `given refresh is true when getCard then service gets called`() {
        val card = TestDataProvider.provideCard(accountID = cardId)
        whenever(service.getCard(any())).thenReturn(card.right())

        val result = sut.getCard(GetCardParams(cardId = cardId, refresh = true))

        verify(service).getCard(any())
        verify(cardLocalRepo).saveCard(card)
        verifyNoMoreInteractions(cardLocalRepo)
        result.shouldBeRightAndEqualTo(card)
    }

    @Test
    fun `when unlockCard then service gets called`() {
        sut.unlockCard(cardId)

        verify(service).unlockCard(cardId)
    }

    @Test
    fun `when unlockCard then service answer is returned`() {
        whenever(service.unlockCard(cardId)).thenReturn(card.right())

        val result = sut.unlockCard(cardId)

        result.shouldBeRightAndEqualTo(card)
    }

    @Test
    fun `when lockCard then service gets called`() {
        sut.lockCard(cardId)

        verify(service).lockCard(cardId)
    }

    @Test
    fun `when lockCard then service answer is returned`() {
        whenever(service.lockCard(cardId)).thenReturn(card.right())

        val result = sut.lockCard(cardId)

        result.shouldBeRightAndEqualTo(card)
    }

    @Test
    fun `when activatePhysicalCard then service gets called`() {
        sut.activatePhysicalCard(cardId, CODE)

        verify(service).activatePhysicalCard(cardId, CODE)
    }

    @Test
    fun `when activatePhysicalCard then service answer is returned`() {
        val activateResult = ActivatePhysicalCardResult(ActivatePhysicalCardResultType.ACTIVATED)
        whenever(service.activatePhysicalCard(cardId, CODE)).thenReturn(activateResult.right())

        val result = sut.activatePhysicalCard(cardId, CODE)

        result.shouldBeRightAndEqualTo(activateResult)
    }

    @Test
    fun `given refresh in false and no localData when getCardBalance then service gets called`() {
        val balance = TestDataProvider.provideBalance()
        whenever(cardBalanceLocalDao.getCardBalance(cardId)).thenReturn(null)
        whenever(service.getCardBalance(cardId)).thenReturn(balance.right())

        val result = sut.getCardBalance(GetCardBalanceParams(cardId, refresh = false))

        verify(service).getCardBalance(cardId)
        result.shouldBeRightAndEqualTo(balance)
    }

    @Test
    fun `given refresh in false and localData exists when getCardBalance then service doesn't gets called`() {
        val balance = TestDataProvider.provideBalance()
        whenever(cardBalanceLocalDao.getCardBalance(cardId)).thenReturn(
            CardBalanceLocalEntity.fromBalance(
                cardId,
                balance
            )
        )

        val result = sut.getCardBalance(GetCardBalanceParams(cardId, refresh = false))

        verifyZeroInteractions(service)
        verify(cardBalanceLocalDao).getCardBalance(cardId)
        result.shouldBeRightAndEqualTo(balance)
    }

    @Test
    fun `given refresh in true when getCardBalance then service gets called`() {
        val balance = TestDataProvider.provideBalance()
        whenever(service.getCardBalance(cardId)).thenReturn(balance.right())

        val result = sut.getCardBalance(GetCardBalanceParams(cardId, refresh = true))

        verify(cardBalanceLocalDao).saveCardBalance(any())
        result.shouldBeRightAndEqualTo(balance)
    }

    @Test
    fun `when setCardBalance then service gets called`() {
        val fundingId = "f_id"
        sut.setCardBalance(SetCardBalanceParams(cardId, fundingId))

        verify(service).setCardBalance(cardId, fundingId)
    }

    @Test
    fun `when setPin then service gets called`() {
        sut.setPin(SetPinParams(cardId, CODE))

        verify(service).setPin(cardId, CODE)
    }

    @Test
    fun `when setCardPasscode then service gets called`() {
        val verificationId = "id_123"
        sut.setCardPasscode(cardId = cardId, passcode = CODE, verificationId = verificationId)

        verify(service).setCardPasscode(cardId, CODE, verificationId)
    }
}
