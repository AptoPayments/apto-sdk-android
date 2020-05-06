package com.aptopayments.core.repository.stats.usecases

import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.stats.StatsRepository

internal class ClearMonthlySpendingCacheUseCase constructor(
        private val repository: StatsRepository,
        networkHandler: NetworkHandler
) : UseCase<Unit, Unit>(networkHandler) {
    override fun run(params: Unit) = repository.invalidateMonthlySpendingCache()
}
