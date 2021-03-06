package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.UserRepository

internal class CreateUserUseCase(
    private val userRepository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<User, CreateUserUseCase.Params>(networkHandler) {

    data class Params(
        val userData: DataPointList,
        val custodianUid: String? = null,
        val metadata: String? = null
    )

    override fun run(params: Params) = userRepository.createUser(
        userData = params.userData,
        custodianUid = params.custodianUid,
        metadata = params.metadata
    )
}
