package com.aptopayments.mobile.repository.payment.usecases

import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.payment.Payment
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.payment.PaymentRepository
import com.aptopayments.mobile.repository.payment.usecases.PushFundsUseCase.Params

internal class PushFundsUseCase(
    private val repository: PaymentRepository,
    networkHandler: NetworkHandler
) : UseCase<Payment, Params>(networkHandler) {
    override fun run(params: Params) =
        repository.pushFunds(balanceId = params.balanceId, paymentSourceId = params.paymentSourceId, amount = params.amount)

    data class Params(
        val balanceId: String,
        val paymentSourceId: String,
        val amount: Money
    )
}
