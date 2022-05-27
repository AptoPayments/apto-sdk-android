package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.UserRepository

internal class CreateUserSignedPayloadUseCase(
    private val userRepository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<User, String>(networkHandler) {

    override fun run(params: String) = userRepository.createUser(params)
}
