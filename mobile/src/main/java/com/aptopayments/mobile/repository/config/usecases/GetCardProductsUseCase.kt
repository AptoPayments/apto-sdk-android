package com.aptopayments.mobile.repository.config.usecases

import com.aptopayments.mobile.data.cardproduct.CardProductSummary
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.config.ConfigRepository

internal class GetCardProductsUseCase constructor(
    private val repository: ConfigRepository,
    networkHandler: NetworkHandler
) : UseCase<List<CardProductSummary>, Unit>(networkHandler) {
    override fun run(params: Unit) = repository.getCardProducts()
}
