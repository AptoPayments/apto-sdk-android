package com.aptopayments.mobile.repository.voip

import com.aptopayments.mobile.data.voip.VoipCall
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.voip.remote.VoipService
import com.aptopayments.mobile.repository.voip.remote.requests.GetTokensRequest
import com.aptopayments.mobile.repository.voip.usecases.SetupVoipCallParams

internal interface VoipRepository : BaseNoNetworkRepository {

    fun setupVoIPCall(params: SetupVoipCallParams): Either<Failure, VoipCall>
}

internal class VoipRepositoryImpl(
    private val service: VoipService
) : VoipRepository {

    override fun setupVoIPCall(params: SetupVoipCallParams): Either<Failure, VoipCall> {
        return service.getTokens(GetTokensRequest(params.cardId, params.action.source))
    }
}
