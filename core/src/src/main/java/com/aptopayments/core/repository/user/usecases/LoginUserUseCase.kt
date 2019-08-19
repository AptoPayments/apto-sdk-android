package com.aptopayments.core.repository.user.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.user.User
import com.aptopayments.core.data.user.Verification
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.user.UserRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class LoginUserUseCase constructor(
        private val repository: UserRepository,
        networkHandler: NetworkHandler
) : UseCase<User, List<Verification>>(networkHandler) {

    override fun run(params: List<Verification>) = repository.loginUser(params)
}
