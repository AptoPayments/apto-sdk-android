package com.aptopayments.core.repository.statements.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.statements.remote.requests.MonthlyStatementRequest

internal class MonthlyStatementService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val monthlyStatementsApi by lazy {
        apiCatalog.api().create(MonthlyStatementApi::class.java)
    }

    fun getMonthlyStatement(month: Int, year: Int) =
        monthlyStatementsApi.getMonthlyStatement(
            userToken = authorizationHeader(userSessionRepository.userToken),
            request = MonthlyStatementRequest(month, year)
        )

    fun getMonthlyStatementPeriod() =
        monthlyStatementsApi.getMonthlyStatementPeriod(
            userToken = authorizationHeader(userSessionRepository.userToken)
        )
}
