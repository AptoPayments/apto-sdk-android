package com.aptopayments.mobile.repository.paymentsources.usecases

import com.aptopayments.mobile.data.paymentsources.NewPaymentSource
import com.aptopayments.mobile.data.paymentsources.PaymentSource
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.paymentsources.PaymentSourcesRepository

internal class AddPaymentSourceUseCase(
    private val repository: PaymentSourcesRepository,
    networkHandler: NetworkHandler
) : UseCase<PaymentSource, NewPaymentSource>(networkHandler) {
    override fun run(params: NewPaymentSource) = repository.addPaymentSource(params)
}
