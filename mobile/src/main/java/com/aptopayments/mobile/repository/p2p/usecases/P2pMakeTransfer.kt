package com.aptopayments.mobile.repository.p2p.usecases

import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.transfermoney.P2pTransferResponse
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.p2p.P2pRepository

internal class P2pMakeTransfer(
    private val repository: P2pRepository,
    networkHandler: NetworkHandler
) : UseCase<P2pTransferResponse, P2pMakeTransfer.Params>(networkHandler) {

    override fun run(params: Params): Either<Failure, P2pTransferResponse> {
        return repository.makeTransfer(params.sourceId, params.recipientId, params.amount)
    }

    data class Params(
        val sourceId: String,
        val recipientId: String,
        val amount: Money
    )
}
