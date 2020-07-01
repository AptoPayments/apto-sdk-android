package com.aptopayments.mobile.repository.statements.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseService
import com.aptopayments.mobile.repository.statements.remote.requests.MonthlyStatementRequest

internal class MonthlyStatementService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val monthlyStatementsApi by lazy { apiCatalog.api().create(MonthlyStatementApi::class.java) }

    fun getMonthlyStatement(month: Int, year: Int) =
        monthlyStatementsApi.getMonthlyStatement(request = MonthlyStatementRequest(month, year))

    fun getMonthlyStatementPeriod() = monthlyStatementsApi.getMonthlyStatementPeriod()
}
