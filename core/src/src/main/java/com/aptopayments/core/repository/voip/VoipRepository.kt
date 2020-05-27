package com.aptopayments.core.repository.voip

import com.aptopayments.core.data.voip.VoipCall
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.platform.BaseRepository
import com.aptopayments.core.repository.voip.remote.VoipService
import com.aptopayments.core.repository.voip.remote.entities.VoipCallEntity
import com.aptopayments.core.repository.voip.remote.requests.GetTokensRequest
import com.aptopayments.core.repository.voip.usecases.SetupVoipCallParams

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
