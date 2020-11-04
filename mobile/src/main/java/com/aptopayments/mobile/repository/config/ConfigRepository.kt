package com.aptopayments.mobile.repository.config

import com.aptopayments.mobile.data.cardproduct.CardProduct
import com.aptopayments.mobile.data.cardproduct.CardProductSummary
import com.aptopayments.mobile.data.config.ContextConfiguration
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.config.remote.ConfigService

internal interface ConfigRepository : BaseNoNetworkRepository {
    fun getContextConfiguration(forceRefresh: Boolean = false): Either<Failure, ContextConfiguration>
    fun getCardProduct(cardProductId: String, forceRefresh: Boolean = false): Either<Failure, CardProduct>
    fun getCardProducts(): Either<Failure, List<CardProductSummary>>
}

internal class ConfigRepositoryImpl constructor(
    private val service: ConfigService
) : ConfigRepository {

    private var contextConfigurationCache: ContextConfiguration? = null
    private var cardProductCache: HashMap<String, CardProduct> = HashMap()

    override fun getContextConfiguration(forceRefresh: Boolean): Either<Failure, ContextConfiguration> {
        return if (!forceRefresh && contextConfigurationCache != null) {
            contextConfigurationCache!!.right()
        } else {
            service.getContextConfiguration().runIfRight { config ->
                this.contextConfigurationCache = config
            }
        }
    }

    override fun getCardProduct(cardProductId: String, forceRefresh: Boolean): Either<Failure, CardProduct> {
        return if (!forceRefresh && cardProductCache.containsKey(cardProductId)) {
            return Either.Right(cardProductCache[cardProductId]!!)
        } else {
            service.getCardProduct(cardProductId).runIfRight { cardProductCache[cardProductId] = it }
        }
    }

    override fun getCardProducts() = service.getCardProducts()
}
