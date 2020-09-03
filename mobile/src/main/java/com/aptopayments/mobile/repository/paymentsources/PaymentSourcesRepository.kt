package com.aptopayments.mobile.repository.paymentsources

import com.aptopayments.mobile.data.paymentsources.NewPaymentSource
import com.aptopayments.mobile.data.paymentsources.PaymentSource
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.platform.BaseNoNetworkRepository

internal interface PaymentSourcesRepository : BaseNoNetworkRepository {
    fun addPaymentSource(source: NewPaymentSource): Either<Failure, PaymentSource>

    fun getPaymentSources(startingAfter: String?, endingBefore: String?, limit: Int?): Either<Failure, List<PaymentSource>>

    fun deletePaymentSource(paymentSourceId: String): Either<Failure, Unit>
}

internal class PaymentSourcesRepositoryImpl(private val service: PaymentSourcesService) : PaymentSourcesRepository {

    override fun addPaymentSource(source: NewPaymentSource): Either<Failure, PaymentSource> {
        return service.addPaymentSource(source)
    }

    override fun getPaymentSources(startingAfter: String?, endingBefore: String?, limit: Int?): Either<Failure, List<PaymentSource>> {
        return service.getPaymentSources(startingAfter, endingBefore, limit)
    }

    override fun deletePaymentSource(paymentSourceId: String) = service.deletePaymentSource(paymentSourceId)
}
