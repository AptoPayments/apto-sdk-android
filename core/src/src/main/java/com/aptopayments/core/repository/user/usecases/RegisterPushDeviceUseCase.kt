package com.aptopayments.core.repository.user.usecases

import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository

internal class RegisterPushDeviceUseCase constructor(
    private val repository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<Unit, String>(networkHandler) {

    override fun run(params: String) = repository.registerPushDevice(params)
}
