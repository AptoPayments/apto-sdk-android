package com.aptopayments.mobile.repository.p2p.remote

import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.transfermoney.P2pTransferResponse
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.p2p.remote.request.P2pMakeTransferRequest

internal class P2pService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val api by lazy { apiCatalog.api().create(TransferMoneyApi::class.java) }

    fun findRecipient(phone: PhoneNumber? = null, email: String? = null) =
        request(api.findRecipient(phone?.countryCode, phone?.phoneNumber, email), { it.toModelObject() })

    fun makeTransfer(sourceId: String, recipientId: String, amount: Money): Either<Failure, P2pTransferResponse> {
        val requestBody = P2pMakeTransferRequest.create(sourceId, recipientId, amount)
        return request(api.makeTransfer(requestBody), { it.toModelObject() })
    }
}
