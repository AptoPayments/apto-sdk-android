package com.aptopayments.mobile.repository.config.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.repository.config.remote.entities.CardConfigurationEntity
import com.aptopayments.mobile.repository.config.remote.entities.ContextConfigurationEntity
import org.junit.jupiter.api.Test
import org.koin.core.inject
import kotlin.test.assertTrue

private const val CARD_PRODUCT_ID = "id_12346"

internal class ConfigServiceTest : NetworkServiceTest() {

    private val sut: ConfigService by inject()

    @Test
    fun `when startCardApplication then request is made to the correct url`() {
        enqueueFile("configResponse200.json")

        val response = sut.getContextConfiguration()

        assertRequestSentTo("v1/config", "GET")
        assertTrue(response.isRight)
    }

    @Test
    fun `when startCardApplication then response parses correctly`() {
        enqueueFile("configResponse200.json")
        val fileContent = readFile("configResponse200.json")
        val configEntity = parseEntity(fileContent, ContextConfigurationEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.getContextConfiguration()

        response.shouldBeRightAndEqualTo(configEntity.toContextConfiguration())
    }

    @Test
    fun `when getCardProducts then request is made to the correct url`() {
        enqueueFile("configCardProductsResponse200.json")

        val response = sut.getCardProducts()

        assertRequestSentTo("v1/config/cardproducts", "GET")
        assertTrue(response.isRight)
    }

    @Test
    fun `when getCardProduct then request is made to the correct url`() {
        enqueueFile("configCardproductsIdResponse200.json")

        val response = sut.getCardProduct(CARD_PRODUCT_ID)

        assertRequestSentTo("v1/config/cardproducts/$CARD_PRODUCT_ID", "GET")
        assertTrue(response.isRight)
    }

    @Test
    fun `when getCardProduct then response parses correctly`() {
        val fileContent = readFile("configCardproductsIdResponse200.json")
        val configEntity = parseEntity(fileContent, CardConfigurationEntity::class.java).toCardProduct()
        enqueueContent(fileContent)

        val response = sut.getCardProduct(CARD_PRODUCT_ID)

        response.shouldBeRightAndEqualTo(configEntity)
    }
}
