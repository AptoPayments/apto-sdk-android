package com.aptopayments.mobile.repository.paymentsources

import com.aptopayments.mobile.data.paymentsources.NewCard
import com.aptopayments.mobile.data.paymentsources.NewPaymentSource
import com.aptopayments.mobile.data.paymentsources.PaymentSource
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.paymentsources.remote.requests.AddPaymentSourceRequest
import com.aptopayments.mobile.repository.paymentsources.remote.requests.AddPaymentSourceType
import com.aptopayments.mobile.repository.paymentsources.remote.requests.CardRequest

private const val MAX_PAYMENT_SOURCES_PAGE = 50

internal class PaymentSourcesService constructor(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val api by lazy { apiCatalog.api().create(PaymentSourcesApi::class.java) }
    private val vaultApi by lazy { apiCatalog.vaultApi().create(PaymentSourcesApi::class.java) }

    fun addPaymentSource(source: NewPaymentSource): Either<Failure, PaymentSource> {
        val request = getAddPaymentSourceRequest(source)
        return request(vaultApi.addPaymentSource(request = request), { it.paymentSource.toPaymentSource() })
    }

    fun getPaymentSources(
        startingAfter: String? = null,
        endingBefore: String? = null,
        limit: Int? = MAX_PAYMENT_SOURCES_PAGE
    ): Either<Failure, List<PaymentSource>> =
        request(api.getPaymentSourcesList(startingAfter, endingBefore, limit), { it.toPaymentSourcesList() })

    fun deletePaymentSource(
        paymentSourceId: String
    ): Either<Failure, Unit> {
        return request(api.deletePaymentSource(paymentSourceId), { Unit })
    }

    private fun getAddPaymentSourceRequest(source: NewPaymentSource): AddPaymentSourceRequest {
        return when (source) {
            is NewCard -> getAddCardPaymentSourceRequest(source)
            else -> throw RuntimeException("Unrecognized Payment Source")
        }
    }

    private fun getAddCardPaymentSourceRequest(source: NewCard): AddPaymentSourceRequest {
        val card = CardRequest(
            pan = source.pan,
            cvv = source.cvv,
            expiration = getNewCardExpiration(source.expirationMonth, source.expirationYear),
            lastFour = source.pan.takeLast(4),
            postalCode = source.zipCode
        )
        return AddPaymentSourceRequest(
            type = AddPaymentSourceType.CARD.toString(),
            description = source.description,
            card = card
        )
    }

    private fun getNewCardExpiration(month: String, year: String): String {
        val yearFourDigits = if (year.length == 2) "20$year" else year
        return "$yearFourDigits-$month"
    }
}
