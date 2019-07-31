package com.aptopayments.core.repository.config.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.cardproduct.CardProduct
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.config.ConfigRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetCardProductUseCase @Inject constructor(
        private val repository: ConfigRepository,
        networkHandler: NetworkHandler
) : UseCase<CardProduct, GetCardProductParams>(networkHandler) {
    override fun run(params: GetCardProductParams) = repository.getCardProduct(
            params.cardProductId, params.refresh)
}
