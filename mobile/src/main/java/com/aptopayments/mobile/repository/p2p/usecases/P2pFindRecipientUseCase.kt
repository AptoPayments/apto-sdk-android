package com.aptopayments.mobile.repository.p2p.usecases

import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.transfermoney.CardHolderData
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.exception.NoEnoughParametersFailure
import com.aptopayments.mobile.exception.TooMuchParametersFailure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.left
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.p2p.P2pRepository

internal class P2pFindRecipientUseCase(
    private val repository: P2pRepository,
    networkHandler: NetworkHandler
) : UseCase<CardHolderData, P2pFindRecipientUseCase.Params>(networkHandler) {

    override fun run(params: Params): Either<Failure, CardHolderData> {
        return when {
            params.phone == null && params.email == null -> NoEnoughParametersFailure().left()
            params.phone != null && params.email != null -> TooMuchParametersFailure().left()
            else -> repository.findRecipient(params.phone, params.email)
        }
    }

    data class Params(
        val phone: PhoneNumber? = null,
        val email: String? = null,
    )
}
