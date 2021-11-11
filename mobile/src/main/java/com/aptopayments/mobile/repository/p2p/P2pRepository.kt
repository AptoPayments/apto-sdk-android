package com.aptopayments.mobile.repository.p2p

import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.transfermoney.CardHolderData
import com.aptopayments.mobile.data.transfermoney.P2pTransferResponse
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.p2p.remote.P2pService

internal interface P2pRepository : BaseNoNetworkRepository {
    fun findRecipient(phoneNumber: PhoneNumber? = null, email: String? = null): Either<Failure, CardHolderData>
    fun makeTransfer(sourceId: String, recipientId: String, amount: Money): Either<Failure, P2pTransferResponse>
}

internal class P2pRepositoryImpl(private val service: P2pService) : P2pRepository {

    override fun findRecipient(phoneNumber: PhoneNumber?, email: String?) =
        service.findRecipient(phoneNumber, email)

    override fun makeTransfer(sourceId: String, recipientId: String, amount: Money) =
        service.makeTransfer(sourceId, recipientId, amount)
}
