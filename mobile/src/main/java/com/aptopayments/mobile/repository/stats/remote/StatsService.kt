package com.aptopayments.mobile.repository.stats.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.stats.remote.entities.MonthlySpendingEntity

internal class StatsService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val statsApi by lazy { apiCatalog.api().create(StatsApi::class.java) }

    fun getMonthlySpending(cardId: String, month: String, year: String) =
        request(
            statsApi.getMonthlySpending(cardId = cardId, month = month, year = year),
            { it.toMonthlySpending() },
            MonthlySpendingEntity()
        )
}
