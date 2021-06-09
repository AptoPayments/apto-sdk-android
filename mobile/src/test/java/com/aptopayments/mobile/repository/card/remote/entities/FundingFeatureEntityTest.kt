package com.aptopayments.mobile.repository.card.remote.entities

import com.aptopayments.mobile.common.ModelDataProvider
import com.aptopayments.mobile.data.card.Card
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private const val SOFT_DESCRIPTOR = "soft descriptor"

class FundingFeatureEntityTest {
    private val cardNetworks = listOf("visa", "mastercard")

    @Test
    fun `isEnabled is true when enabled provided`() {
        val sut =
            FundingFeatureEntity("enabled", cardNetworks, ModelDataProvider.fundingLimitsEntity(), SOFT_DESCRIPTOR)

        val element = sut.toFundingFeature()

        assertTrue(element.isEnabled)
    }

    @Test
    fun `isEnabled is false when disabled provided`() {
        val sut =
            FundingFeatureEntity("disabled", cardNetworks, ModelDataProvider.fundingLimitsEntity(), SOFT_DESCRIPTOR)

        val element = sut.toFundingFeature()

        assertFalse(element.isEnabled)
    }

    @Test
    fun `cardNetworks are parsed correctly`() {
        val sut =
            FundingFeatureEntity("enabled", cardNetworks, ModelDataProvider.fundingLimitsEntity(), SOFT_DESCRIPTOR)

        val element = sut.toFundingFeature()

        assertEquals(listOf(Card.CardNetwork.VISA, Card.CardNetwork.MASTERCARD), element.cardNetworks)
    }
}
