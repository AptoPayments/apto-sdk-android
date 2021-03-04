package com.aptopayments.mobile.repository.fundingsources.remote

import com.aptopayments.mobile.data.fundingsources.AchAccountDetails
import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.fundingsources.remote.entities.AchAccountDetailsEntity
import org.koin.core.inject

internal class FundingSourcesService(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val fundingSourcesApi by lazy { apiCatalog.api().create(FundingSourcesApi::class.java) }
    val userSessionRepository: UserSessionRepository by inject()

    fun getFundingSources(accountId: String, page: Int, rows: Int): Either<Failure, List<Balance>> =
        request(
            fundingSourcesApi.getFundingSources(accountID = accountId, page = page, rows = rows),
            { list -> list.data?.map { it.toBalance() } ?: emptyList() },
            ListEntity()
        )

    fun assignAchAccountToBalance(balanceId: String): Either<Failure, AchAccountDetails> =
        request(
            fundingSourcesApi.assignAchAccountToBalance(balanceId),
            { it.toAchAccountDetails() },
            AchAccountDetailsEntity()
        )

    fun getAchAccountDetails(balanceId: String): Either<Failure, AchAccountDetails> =
        request(
            fundingSourcesApi.getAchAccountDetails(balanceId),
            { it.toAchAccountDetails() },
            AchAccountDetailsEntity()
        )
}
