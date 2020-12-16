package com.aptopayments.mobile.repository.statements.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.statements.remote.entities.MonthlyStatementReportEntity
import com.aptopayments.mobile.repository.statements.remote.requests.MonthlyStatementRequest

internal class MonthlyStatementService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val api by lazy { apiCatalog.api().create(MonthlyStatementApi::class.java) }

    fun getMonthlyStatement(month: Int, year: Int) =
        request(
            call = api.getMonthlyStatement(request = MonthlyStatementRequest(month, year)),
            transform = { it.toMonthlyStatementReport() },
            default = MonthlyStatementReportEntity(month, year)
        )

    fun getMonthlyStatementPeriod() = request(api.getMonthlyStatementPeriod(), { it.toMonthlyStatementPeriod() })
}
