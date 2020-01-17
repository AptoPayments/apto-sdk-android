package com.aptopayments.core.repository

import com.aptopayments.core.UnitTest
import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.exception.Failure.NetworkConnection
import com.aptopayments.core.functional.Either
import com.aptopayments.core.functional.Either.Right
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.config.ConfigRepository
import com.aptopayments.core.repository.config.remote.ConfigService
import com.aptopayments.core.repository.config.remote.entities.CardConfigurationEntity
import com.aptopayments.core.repository.config.remote.entities.CardProductSummaryEntity
import com.aptopayments.core.repository.config.remote.entities.ContextConfigurationEntity
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.mockito.Mock
import retrofit2.Call
import retrofit2.Response

class ConfigRepositoryTest : UnitTest() {

    private lateinit var sut: ConfigRepository.Network

    @Mock private lateinit var networkHandler: NetworkHandler
    @Mock private lateinit var service: ConfigService

    @Mock private lateinit var getConfigCall: Call<ContextConfigurationEntity>
    @Mock private lateinit var getConfigResponse: Response<ContextConfigurationEntity>
    @Mock private lateinit var contextConfigurationEntity: ContextConfigurationEntity

    @Mock private lateinit var getCardConfigCall: Call<CardConfigurationEntity>
    @Mock private lateinit var getCardConfigResponse: Response<CardConfigurationEntity>

    @Mock private lateinit var getCardProductsCall: Call<ListEntity<CardProductSummaryEntity>>
    @Mock private lateinit var getCardProductsResponse: Response<ListEntity<CardProductSummaryEntity>>

    @Before
    override fun setUp() {
        super.setUp()
        startKoin {
            modules(module {
                single { networkHandler }
                single { service }
            })
        }
        sut = ConfigRepository.Network(networkHandler, service)
    }

    @Ignore
    @Test fun `Get context configuration should delegate to the service`() {
        val testContextConfiguration = TestDataProvider.provideContextConfiguration()
        given { networkHandler.isConnected }.willReturn(true)
        given { getConfigResponse.body() }.willReturn(ContextConfigurationEntity())
        given { getConfigResponse.isSuccessful }.willReturn(true)
        given { getConfigCall.execute() }.willReturn(getConfigResponse)
        given { service.getContextConfiguration() }.willReturn(getConfigCall)
        given { contextConfigurationEntity.toContextConfiguration() }
                .willReturn(testContextConfiguration)

        val contextConfig = sut.getContextConfiguration()

        contextConfig shouldEqual Right(ContextConfigurationEntity())

        verify(service).getContextConfiguration()
    }

    @Test fun `Get context configuration should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val result = sut.getContextConfiguration()

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `Get context configuration should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val result = sut.getContextConfiguration()

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `Get card configuration should delegate to the service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { getCardConfigResponse.body() }.willReturn(CardConfigurationEntity())
        given { getCardConfigResponse.isSuccessful }.willReturn(true)
        given { getCardConfigCall.execute() }.willReturn(getCardConfigResponse)
        given { service.getCardProduct(cardProductId = "") }.willReturn(getCardConfigCall)

        sut.getCardProduct(cardProductId = "")
        verify(service).getCardProduct(cardProductId = "")
    }

    @Test fun `Get card configuration should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val result = sut.getCardProduct(cardProductId = "")

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `Get card configuration should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val result = sut.getCardProduct(cardProductId = "")

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }


    @Test fun `Get card products should delegate to the service`() {
        given { networkHandler.isConnected }.willReturn(true)
        given { getCardProductsResponse.body() }.willReturn(ListEntity())
        given { getCardProductsResponse.isSuccessful }.willReturn(true)
        given { getCardProductsCall.execute() }.willReturn(getCardProductsResponse)
        given { service.getCardProducts() }.willReturn(getCardProductsCall)

        sut.getCardProducts()
        verify(service).getCardProducts()
    }

    @Test fun `Get card products should return network failure when no connection`() {
        given { networkHandler.isConnected }.willReturn(false)

        val result = sut.getCardProducts()

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }

    @Test fun `Get card products should return network failure when undefined connection`() {
        given { networkHandler.isConnected }.willReturn(null)

        val result = sut.getCardProducts()

        result shouldBeInstanceOf Either::class.java
        result.isLeft shouldEqual true
        result.either({ failure -> failure shouldBeInstanceOf NetworkConnection::class.java }, {})
        verifyZeroInteractions(service)
    }
}
