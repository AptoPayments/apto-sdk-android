package com.aptopayments.mobile.repository.stats.usecases

import com.aptopayments.mobile.data.stats.MonthlySpending
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.stats.StatsRepository

internal class GetMonthlySpendingUseCase constructor(
    private val repository: StatsRepository,
    networkHandler: NetworkHandler
) : UseCase<MonthlySpending, GetMonthlySpendingUseCase.Params>(networkHandler) {
    override fun run(params: Params) = repository.getMonthlySpending(params.cardId, params.month, params.year)

    data class Params(
        val cardId: String,
        val month: String,
        val year: String
    )
}
