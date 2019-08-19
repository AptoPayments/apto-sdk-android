package com.aptopayments.core.repository.fundingsources.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.repository.UserSessionRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class FundingSourcesService constructor(apiCatalog: ApiCatalog) : KoinComponent {

    private val fundingSourcesApi by lazy { apiCatalog.api().create(FundingSourcesApi::class.java) }
    val userSessionRepository: UserSessionRepository by inject()

    fun getFundingSources(accountId: String) = fundingSourcesApi.getFundingSources(
            apiKey = ApiCatalog.apiKey,
            userToken = userSessionRepository.userToken,
            accountID = accountId
    )
}
