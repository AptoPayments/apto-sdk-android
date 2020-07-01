package com.aptopayments.mobile.repository.stats.usecases

import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.stats.StatsRepository

internal class ClearMonthlySpendingCacheUseCase constructor(
    private val repository: StatsRepository,
    networkHandler: NetworkHandler
) : UseCase<Unit, Unit>(networkHandler) {
    override fun run(params: Unit) = repository.invalidateMonthlySpendingCache()
}
