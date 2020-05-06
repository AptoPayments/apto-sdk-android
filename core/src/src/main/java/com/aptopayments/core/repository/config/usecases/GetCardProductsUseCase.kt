package com.aptopayments.core.repository.config.usecases

import com.aptopayments.core.data.cardproduct.CardProductSummary
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.config.ConfigRepository

internal class GetCardProductsUseCase constructor(
        private val repository: ConfigRepository,
        networkHandler: NetworkHandler
) : UseCase<List<CardProductSummary>, Unit>(networkHandler) {
    override fun run(params: Unit) = repository.getCardProducts()
}
