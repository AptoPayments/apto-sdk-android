package com.aptopayments.mobile.repository.fundingsources.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.UserSessionRepository
import org.koin.core.inject

internal class FundingSourcesService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val fundingSourcesApi by lazy { apiCatalog.api().create(FundingSourcesApi::class.java) }
    val userSessionRepository: UserSessionRepository by inject()

    fun getFundingSources(accountId: String, page: Int, rows: Int) =
        request(
            fundingSourcesApi.getFundingSources(accountID = accountId, page = page, rows = rows),
            { list -> list.data?.map { it.toBalance() } ?: emptyList() },
            ListEntity()
        )
}
