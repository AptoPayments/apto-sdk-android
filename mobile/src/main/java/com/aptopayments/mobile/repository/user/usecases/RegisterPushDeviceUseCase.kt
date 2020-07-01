package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.UserRepository

internal class RegisterPushDeviceUseCase constructor(
    private val repository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<Unit, String>(networkHandler) {

    override fun run(params: String) = repository.registerPushDevice(params)
}
