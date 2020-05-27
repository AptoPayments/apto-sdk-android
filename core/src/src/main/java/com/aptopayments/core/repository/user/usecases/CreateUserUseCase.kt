package com.aptopayments.core.repository.user.usecases

import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.user.User
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository

internal class CreateUserUseCase constructor(
    private val userRepository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<User, CreateUserUseCase.Params>(networkHandler) {

    data class Params(
        val userData: DataPointList,
        val custodianUid: String? = null
    )

    override fun run(params: Params) = userRepository.createUser(
        userData = params.userData,
        custodianUid = params.custodianUid
    )
}
