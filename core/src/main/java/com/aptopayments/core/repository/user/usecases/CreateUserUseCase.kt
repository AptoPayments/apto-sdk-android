package com.aptopayments.core.repository.user.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.user.User
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class CreateUserUseCase @Inject constructor(
        private val userRepository: UserRepository,
        networkHandler: NetworkHandler
) : UseCase<User, DataPointList>(networkHandler) {

    override fun run(params: DataPointList) = userRepository.createUser(userData = params)

}
