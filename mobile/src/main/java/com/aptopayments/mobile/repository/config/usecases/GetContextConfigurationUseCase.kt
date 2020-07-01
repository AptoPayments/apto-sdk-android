package com.aptopayments.mobile.repository.config.usecases

import com.aptopayments.mobile.data.config.ContextConfiguration
import com.aptopayments.mobile.data.config.UIConfig
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.LiteralsRepository
import com.aptopayments.mobile.repository.config.ConfigRepository

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
