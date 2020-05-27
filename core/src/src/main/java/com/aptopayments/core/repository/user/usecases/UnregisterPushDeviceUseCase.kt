package com.aptopayments.core.repository.user.usecases

import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository

internal class UnregisterPushDeviceUseCase constructor(
    private val repository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<Unit, UnregisterPushDeviceParams>(networkHandler) {

    override fun run(params: UnregisterPushDeviceParams) = repository.unregisterPushDevice(params)
}
