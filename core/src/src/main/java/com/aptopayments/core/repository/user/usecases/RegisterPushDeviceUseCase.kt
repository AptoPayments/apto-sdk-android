package com.aptopayments.core.repository.user.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class RegisterPushDeviceUseCase @Inject constructor(
        private val repository: UserRepository,
        networkHandler: NetworkHandler
) : UseCase<Unit, String>(networkHandler) {

    override fun run(params: String) = repository.registerPushDevice(params)
}
