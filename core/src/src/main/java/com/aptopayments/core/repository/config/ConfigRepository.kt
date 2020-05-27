package com.aptopayments.core.repository.config

import com.aptopayments.core.data.cardproduct.CardProduct
import com.aptopayments.core.data.cardproduct.CardProductSummary
import com.aptopayments.core.data.config.ContextConfiguration
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.ListEntity
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.platform.BaseRepository
import com.aptopayments.core.repository.config.remote.ConfigService
import com.aptopayments.core.repository.config.remote.entities.CardConfigurationEntity
import com.aptopayments.core.repository.config.remote.entities.CardProductSummaryEntity
import com.aptopayments.core.repository.config.remote.entities.ContextConfigurationEntity

internal interface ConfigRepository : BaseRepository {

    fun getContextConfiguration(forceRefresh: Boolean = false): Either<Failure, ContextConfiguration>
    fun getCardProduct(cardProductId: String, forceRefresh: Boolean = false): Either<Failure, CardProduct>
    fun getCardProducts(): Either<Failure, List<CardProductSummary>>

    class Network constructor(
        private val networkHandler: NetworkHandler,
        private val service: ConfigService
    ) : BaseRepository.BaseRepositoryImpl(), ConfigRepository {

        private var contextConfigurationCache: ContextConfiguration? = null
        private var cardProductCache: HashMap<String, CardProduct> = HashMap()

        override fun getContextConfiguration(forceRefresh: Boolean): Either<Failure, ContextConfiguration> {
            if (!forceRefresh && contextConfigurationCache != null) {
                return Either.Right(contextConfigurationCache!!)
            }
            if (!networkHandler.isConnected) {
                return Either.Left(Failure.NetworkConnection)
            }
            val result = request(
                service.getContextConfiguration(),
                { it.toContextConfiguration() },
                ContextConfigurationEntity()
            )
            result.either({}, ::handleNewContextConfigurationReceived)
            return result
        }

        private fun handleNewContextConfigurationReceived(contextConfiguration: ContextConfiguration) {
            this.contextConfigurationCache = contextConfiguration
        }

        override fun getCardProduct(cardProductId: String, forceRefresh: Boolean): Either<Failure, CardProduct> {
            if (!forceRefresh && cardProductCache.containsKey(cardProductId)) {
                return Either.Right(cardProductCache[cardProductId]!!)
            }
            if (!networkHandler.isConnected) {
                return Either.Left(Failure.NetworkConnection)
            }
            val result = request(
                service.getCardProduct(cardProductId),
                { it.toCardProduct() },
                CardConfigurationEntity()
            )
            result.either({}, { handleNewCardProductReceived(it, cardProductId) })
            return result
        }

        private fun handleNewCardProductReceived(cardProduct: CardProduct, cardProductId: String) {
            cardProductCache[cardProductId] = cardProduct
        }

        override fun getCardProducts(): Either<Failure, List<CardProductSummary>> {
            return when (networkHandler.isConnected) {
                true -> {
                    return request(service.getCardProducts(), { listEntity: ListEntity<CardProductSummaryEntity> ->
                        listEntity.data?.map {
                            it.toCardProductSummary()
                        } ?: emptyList()
                    }, ListEntity())
                }
                false -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}
