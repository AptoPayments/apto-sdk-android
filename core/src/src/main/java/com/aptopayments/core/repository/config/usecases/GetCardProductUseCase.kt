package com.aptopayments.core.repository.config.usecases

import com.aptopayments.core.data.cardproduct.CardProduct
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.config.ConfigRepository

internal class GetCardProductUseCase constructor(
        private val repository: ConfigRepository,
        networkHandler: NetworkHandler
) : UseCase<CardProduct, GetCardProductParams>(networkHandler) {
    override fun run(params: GetCardProductParams) = repository.getCardProduct(params.cardProductId, params.refresh)
}
