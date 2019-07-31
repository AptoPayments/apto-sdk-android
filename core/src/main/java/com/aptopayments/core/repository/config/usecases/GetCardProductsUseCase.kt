package com.aptopayments.core.repository.config.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.cardproduct.CardProductSummary
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.config.ConfigRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetCardProductsUseCase @Inject constructor(
        private val repository: ConfigRepository,
        networkHandler: NetworkHandler
) : UseCase<List<CardProductSummary>, Unit>(networkHandler) {
    override fun run(params: Unit) = repository.getCardProducts()
}
