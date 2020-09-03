package com.aptopayments.mobile.repository.payment

import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.payment.Payment
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.platform.BaseNoNetworkRepository

internal interface PaymentRepository : BaseNoNetworkRepository {
    fun pushFunds(accountId: String, paymentSourceId: String, amount: Money): Either<Failure, Payment>
}

internal class PaymentRepositoryImpl(private val service: PaymentService) : PaymentRepository {

    override fun pushFunds(balanceId: String, paymentSourceId: String, amount: Money): Either<Failure, Payment> {
        return service.pushFunds(balanceId = balanceId, sourceId = paymentSourceId, amount = amount)
    }
}
