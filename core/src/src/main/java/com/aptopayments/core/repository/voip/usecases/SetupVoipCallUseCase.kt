package com.aptopayments.core.repository.voip.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.voip.VoipCall
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.voip.VoipRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class SetupVoipCallUseCase @Inject constructor(
        private val repository: VoipRepository,
        networkHandler: NetworkHandler
) : UseCase<VoipCall, SetupVoipCallParams>(networkHandler) {
    override fun run(params: SetupVoipCallParams) = repository.setupVoIPCall(params)
}
