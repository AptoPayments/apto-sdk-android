package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.data.user.Verification
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.UserRepository

internal class LoginUserUseCase constructor(
    private val repository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<User, List<Verification>>(networkHandler) {

    override fun run(params: List<Verification>) = repository.loginUser(params)
}
