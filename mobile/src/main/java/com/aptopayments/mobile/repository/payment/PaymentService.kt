package com.aptopayments.mobile.repository.payment

import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.payment.Payment
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.card.remote.entities.MoneyEntity
import com.aptopayments.mobile.repository.payment.remote.requests.PushFundsRequest

internal class PaymentService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val api by lazy { apiCatalog.api().create(PaymentApi::class.java) }

    fun pushFunds(balanceId: String, sourceId: String, amount: Money): Either<Failure, Payment> {
        val request = PushFundsRequest(
            amount = MoneyEntity(amount = amount.amount, currency = amount.currency),
            balanceId = balanceId
        )
        return request(api.pushFunds(sourceId, request), { it.payment.toPayment() })
    }
}
