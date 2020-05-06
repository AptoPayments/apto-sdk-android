package com.aptopayments.core.repository.voip.usecases

import com.aptopayments.core.data.voip.VoipCall
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.voip.VoipRepository

internal class SetupVoipCallUseCase constructor(
        private val repository: VoipRepository,
        networkHandler: NetworkHandler
) : UseCase<VoipCall, SetupVoipCallParams>(networkHandler) {
    override fun run(params: SetupVoipCallParams) = repository.setupVoIPCall(params)
}
