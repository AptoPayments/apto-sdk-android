package com.aptopayments.mobile.repository.fundingsources.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.repository.UserSessionRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class FundingSourcesService constructor(apiCatalog: ApiCatalog) : KoinComponent {

    private val fundingSourcesApi by lazy { apiCatalog.api().create(FundingSourcesApi::class.java) }
    val userSessionRepository: UserSessionRepository by inject()

    fun getFundingSources(accountId: String, page: Int, rows: Int) =
        fundingSourcesApi.getFundingSources(accountID = accountId, page = page, rows = rows)
}
