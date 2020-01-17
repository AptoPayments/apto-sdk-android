package com.aptopayments.core.repository.stats.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService

internal class StatsService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val statsApi by lazy { apiCatalog.api().create(StatsApi::class.java) }

    fun getMonthlySpending(cardId: String, month: String, year: String) =
            statsApi.getMonthlySpending(
                    userToken = authorizationHeader(userSessionRepository.userToken),
                    cardId = cardId,
                    month = month,
                    year = year
            )
}
