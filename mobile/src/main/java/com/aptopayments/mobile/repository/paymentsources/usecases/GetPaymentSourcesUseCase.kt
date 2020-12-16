package com.aptopayments.mobile.repository.paymentsources.usecases

import com.aptopayments.mobile.data.paymentsources.PaymentSource
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.paymentsources.PaymentSourcesRepository
import com.aptopayments.mobile.repository.paymentsources.usecases.GetPaymentSourcesUseCase.Params

internal class GetPaymentSourcesUseCase(
    private val repository: PaymentSourcesRepository,
    networkHandler: NetworkHandler
) : UseCase<List<PaymentSource>, Params?>(networkHandler) {
    override fun run(params: Params?) =
        repository.getPaymentSources(params?.startingAfter, params?.endingBefore, params?.limit)

    data class Params(
        val startingAfter: String?,
        val endingBefore: String?,
        val limit: Int?
    )
}
