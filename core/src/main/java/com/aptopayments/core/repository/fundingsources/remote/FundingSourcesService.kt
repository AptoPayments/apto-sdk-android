package com.aptopayments.core.repository.fundingsources.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.repository.UserSessionRepository
import javax.inject.Inject

internal class FundingSourcesService
@Inject constructor(apiCatalog: ApiCatalog) {

    private val fundingSourcesApi by lazy { apiCatalog.api().create(FundingSourcesApi::class.java) }

    @Inject lateinit var userSessionRepository: UserSessionRepository

    fun getFundingSources(accountId: String) =
            fundingSourcesApi.getFundingSources(
                    apiKey = ApiCatalog.apiKey,
                    userToken = userSessionRepository.userToken,
                    accountID = accountId
            )
}
