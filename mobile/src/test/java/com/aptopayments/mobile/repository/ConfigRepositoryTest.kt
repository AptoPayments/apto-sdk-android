package com.aptopayments.mobile.repository

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.cardproduct.CardProductSummary
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.config.ConfigRepositoryImpl
import com.aptopayments.mobile.repository.config.remote.ConfigService
import com.aptopayments.mobile.repository.config.remote.entities.CardConfigurationEntity
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import kotlin.test.assertEquals

private const val CARD_PRODUCT_ID = "id_12345"

class ConfigRepositoryTest {

    private val service: ConfigService = mock()
    private val sut = ConfigRepositoryImpl(service)

    @Test
    fun `Get context configuration should delegate to the service`() {
        val testContextConfiguration = TestDataProvider.provideContextConfiguration()
        given { service.getContextConfiguration() }.willReturn(testContextConfiguration.right())

        val contextConfig = sut.getContextConfiguration()

        assertEquals(testContextConfiguration.right(), contextConfig)
        verify(service).getContextConfiguration()
    }

    @Test
    fun `Get card configuration should delegate to the service`() {
        given { service.getCardProduct(cardProductId = CARD_PRODUCT_ID) }.willReturn(
            CardConfigurationEntity().toCardProduct().right()
        )

        sut.getCardProduct(cardProductId = CARD_PRODUCT_ID)

        verify(service).getCardProduct(cardProductId = CARD_PRODUCT_ID)
    }

    @Test
    fun `Get card products should delegate to the service`() {
        given { service.getCardProducts() }.willReturn(emptyList<CardProductSummary>().right())

        sut.getCardProducts()

        verify(service).getCardProducts()
    }
}
