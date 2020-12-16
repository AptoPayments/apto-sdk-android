package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.user.User
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.UserRepository

internal class UpdateUserDataUseCase(
    private val repository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<User, DataPointList>(networkHandler) {

    override fun run(params: DataPointList) = repository.updateUserData(userData = params)
}
