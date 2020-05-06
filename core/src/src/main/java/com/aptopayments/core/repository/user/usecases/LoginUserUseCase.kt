package com.aptopayments.core.repository.user.usecases

import com.aptopayments.core.data.user.User
import com.aptopayments.core.data.user.Verification
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository

internal class LoginUserUseCase constructor(
        private val repository: UserRepository,
        networkHandler: NetworkHandler
) : UseCase<User, List<Verification>>(networkHandler) {

    override fun run(params: List<Verification>) = repository.loginUser(params)
}
