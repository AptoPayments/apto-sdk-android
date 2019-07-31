package com.aptopayments.core.repository.stats.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.stats.StatsRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class ClearMonthlySpendingCacheUseCase @Inject constructor(
        private val repository: StatsRepository,
        networkHandler: NetworkHandler
) : UseCase<Unit, Unit>(networkHandler) {
    override fun run(params: Unit) = repository.invalidateMonthlySpendingCache()
}
