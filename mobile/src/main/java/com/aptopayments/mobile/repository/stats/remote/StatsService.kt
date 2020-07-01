package com.aptopayments.mobile.repository.stats.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseService

internal class StatsService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val statsApi by lazy { apiCatalog.api().create(StatsApi::class.java) }

    fun getMonthlySpending(cardId: String, month: String, year: String) =
        statsApi.getMonthlySpending(cardId = cardId, month = month, year = year)
}
