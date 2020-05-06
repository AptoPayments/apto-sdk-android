package com.aptopayments.core.repository.user.usecases

import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.user.User
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository

internal class UpdateUserDataUseCase constructor(
        private val repository: UserRepository,
        networkHandler: NetworkHandler
) : UseCase<User, DataPointList>(networkHandler) {

    override fun run(params: DataPointList) = repository.updateUserData(userData = params)
}
