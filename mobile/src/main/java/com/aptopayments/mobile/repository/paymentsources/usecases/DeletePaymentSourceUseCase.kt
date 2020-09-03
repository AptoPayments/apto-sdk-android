package com.aptopayments.mobile.repository.paymentsources.usecases

import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.paymentsources.PaymentSourcesRepository

internal class DeletePaymentSourceUseCase constructor(
    private val repository: PaymentSourcesRepository,
    networkHandler: NetworkHandler
) : UseCase<Unit, String>(networkHandler) {
    override fun run(params: String) = repository.deletePaymentSource(params)
}
