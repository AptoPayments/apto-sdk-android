package com.aptopayments.core.repository.config.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.config.ContextConfiguration
import com.aptopayments.core.data.config.UIConfig
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.config.ConfigRepository
import com.aptopayments.core.repository.LiteralsRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetContextConfigurationUseCase constructor(
        private val repository: ConfigRepository,
        networkHandler: NetworkHandler
) : UseCase<ContextConfiguration, Boolean>(networkHandler) {
    override fun run(params: Boolean): Either<Failure, ContextConfiguration> {
        val result = repository.getContextConfiguration(params)
        result.either({}) { contextConfiguration ->
            UIConfig.updateUIConfigFrom(contextConfiguration.projectConfiguration.branding)
            LiteralsRepository.appendServerLiterals(contextConfiguration.projectConfiguration.labels)
        }
        return result
    }
}
