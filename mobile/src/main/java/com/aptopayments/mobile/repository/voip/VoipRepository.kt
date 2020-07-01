package com.aptopayments.mobile.repository.voip

import com.aptopayments.mobile.data.voip.VoipCall
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.platform.BaseRepository
import com.aptopayments.mobile.repository.voip.remote.VoipService
import com.aptopayments.mobile.repository.voip.remote.entities.VoipCallEntity
import com.aptopayments.mobile.repository.voip.remote.requests.GetTokensRequest
import com.aptopayments.mobile.repository.voip.usecases.SetupVoipCallParams

internal interface VoipRepository : BaseRepository {

    fun setupVoIPCall(params: SetupVoipCallParams): Either<Failure, VoipCall>

    class Network constructor(
        private val networkHandler: NetworkHandler,
        private val service: VoipService
    ) : BaseRepository.BaseRepositoryImpl(), VoipRepository {

        override fun setupVoIPCall(params: SetupVoipCallParams): Either<Failure, VoipCall> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(
                        service.getTokens(GetTokensRequest(params.cardId, params.action.source)),
                        { it.toVoipCall() },
                        VoipCallEntity()
                    )
                }
                false -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}
