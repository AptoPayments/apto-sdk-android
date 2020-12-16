package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.UserRepository

internal class UnregisterPushDeviceUseCase(
    private val repository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<Unit, UnregisterPushDeviceParams>(networkHandler) {

    override fun run(params: UnregisterPushDeviceParams) = repository.unregisterPushDevice(params)
}
