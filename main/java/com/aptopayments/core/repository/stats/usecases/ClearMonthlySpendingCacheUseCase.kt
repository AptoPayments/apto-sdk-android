package com.aptopayments.core.repository.stats.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.stats.StatsRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class ClearMonthlySpendingCacheUseCase constructor(
        private val repository: StatsRepository,
        networkHandler: NetworkHandler
) : UseCase<Unit, Unit>(networkHandler) {
    override fun run(params: Unit) = repository.invalidateMonthlySpendingCache()
}
