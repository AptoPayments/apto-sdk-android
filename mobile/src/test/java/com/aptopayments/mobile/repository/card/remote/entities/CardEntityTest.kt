package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.UnitTest
import com.aptopayments.mobile.common.ModelDataProvider
import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.data.card.KycStatus
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import kotlin.test.assertEquals

class CardEntityTest : UnitTest() {

    @Mock
    private lateinit var mockCardStyleEntity: CardStyleEntity

    @Mock
    private lateinit var mockSpendableAmountEntity: MoneyEntity

    @Mock
    private lateinit var mockNativeAmountEntity: MoneyEntity

    @Mock
    private lateinit var mockFeaturesEntity: FeaturesEntity

    private lateinit var sut: CardEntity

    private val TEST_ACCOUNT_ID = "TEST_ACCOUNT_ID"
    private val TEST_CARD_NETWORK = "TEST_CARD_NETWORK"
    private val TEST_LAST_FOUR_DIGITS = "4512"
    private val TEST_CARD_BRAND = "TEST_CARD_BRAND"
    private val TEST_CARD_ISSUER = "TEST_CARD_ISSUER"
    private val TEST_STATE = "TEST_STATE"
    private val TEST_IS_WAIT_LISTED = false
    private val TEST_KYC_STATUS = "TEST_KYC_STATUS"
    private val TEST_KYC_REASONS = listOf("KYC_REASON")
    private val TEST_ORDERED_STATUS = "TEST_ORDERED_STATUS"
    private val TEST_CARDHOLDER_FIRST_NAME = "TEST_CARDHOLDER_FIRST_NAME"
    private val TEST_CARDHOLDER_LAST_NAME = "TEST_CARDHOLDER_LAST_NAME"
    private val TEST_NAME_ON_CARD = "TEST_NAME_ON_CARD"

    @Before
    fun `set up for testing`() {
        sut = CardEntity(
            accountID = TEST_ACCOUNT_ID,
            cardNetwork = TEST_CARD_NETWORK,
            lastFourDigits = TEST_LAST_FOUR_DIGITS,
            cardBrand = TEST_CARD_BRAND,
            cardIssuer = TEST_CARD_ISSUER,
            state = TEST_STATE,
            isWaitlisted = TEST_IS_WAIT_LISTED,
            kycStatus = TEST_KYC_STATUS,
            kycReason = TEST_KYC_REASONS,
            orderedStatus = TEST_ORDERED_STATUS,
            spendableAmount = mockSpendableAmountEntity,
            nativeSpendableAmount = mockNativeAmountEntity,
            features = mockFeaturesEntity,
            style = mockCardStyleEntity,
            cardholderFirstName = TEST_CARDHOLDER_FIRST_NAME,
            cardholderLastName = TEST_CARDHOLDER_LAST_NAME,
            nameOnCard = TEST_NAME_ON_CARD
        )
    }

    @Test
    fun `card entity converts to card`() {

        // Given
        val testSpendableAmount = ModelDataProvider.money()
        whenever(mockSpendableAmountEntity.toMoney()).thenReturn(testSpendableAmount)

        val testNativeAmount = ModelDataProvider.money()
        whenever(mockNativeAmountEntity.toMoney()).thenReturn(testNativeAmount)

        val testFeatures = ModelDataProvider.features()
        whenever(mockFeaturesEntity.toFeatures()).thenReturn(testFeatures)

        val testCardStyle = ModelDataProvider.cardStyle()
        whenever(mockCardStyleEntity.toCardStyle()).thenReturn(testCardStyle)

        // When
        val card = sut.toCard()

        // Then
        assertEquals(card.accountID, TEST_ACCOUNT_ID)
        assertEquals(card.cardNetwork, Card.CardNetwork.UNKNOWN)
        assertEquals(card.lastFourDigits, TEST_LAST_FOUR_DIGITS)
        assertEquals(card.cardBrand, TEST_CARD_BRAND)
        assertEquals(card.cardIssuer, TEST_CARD_ISSUER)
        assertEquals(card.state, Card.CardState.UNKNOWN)
        assertEquals(card.isWaitlisted, TEST_IS_WAIT_LISTED)
        assertEquals(card.kycStatus, KycStatus.UNKNOWN)
        assertEquals(card.kycReason, TEST_KYC_REASONS)
        assertEquals(card.orderedStatus, Card.OrderedStatus.UNKNOWN)
        assertEquals(card.spendableAmount, testSpendableAmount)
        assertEquals(card.nativeSpendableAmount, testNativeAmount)
        assertEquals(card.features, testFeatures)
        assertEquals(card.cardStyle, testCardStyle)
        assertEquals(card.cardHolder, TEST_NAME_ON_CARD)
    }

    @Test
    fun `name on card takes precedence over legacy first and last name fields`() {
        // When
        val sut = ModelDataProvider.cardEntity(
            style = mockCardStyleEntity,
            nameOnCard = TEST_NAME_ON_CARD
        )
        val card = sut.toCard()
        // Then
        assertEquals(card.cardHolder, TEST_NAME_ON_CARD)
    }

    @Test
    fun `no name on card uses first and last name fields`() {
        // When
        val sut = ModelDataProvider.cardEntity(style = mockCardStyleEntity, nameOnCard = null)
        val card = sut.toCard()
        // Then
        assertEquals(card.cardHolder, "$TEST_CARDHOLDER_FIRST_NAME $TEST_CARDHOLDER_LAST_NAME")
    }
}
