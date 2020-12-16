package com.aptopayments.mobile.repository.voip.usecases

import com.aptopayments.mobile.data.voip.VoipCall
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.voip.VoipRepository

internal class SetupVoipCallUseCase(
    private val repository: VoipRepository,
    networkHandler: NetworkHandler
) : UseCase<VoipCall, SetupVoipCallParams>(networkHandler) {
    override fun run(params: SetupVoipCallParams) = repository.setupVoIPCall(params)
}
