package com.aptopayments.mobile.repository.config.usecases

import com.aptopayments.mobile.data.cardproduct.CardProduct
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.config.ConfigRepository

internal class GetCardProductUseCase(
    private val repository: ConfigRepository,
    networkHandler: NetworkHandler
) : UseCase<CardProduct, GetCardProductParams>(networkHandler) {
    override fun run(params: GetCardProductParams) = repository.getCardProduct(params.cardProductId, params.refresh)
}
