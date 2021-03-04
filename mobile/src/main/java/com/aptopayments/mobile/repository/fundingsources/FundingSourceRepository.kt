package com.aptopayments.mobile.repository.fundingsources

import com.aptopayments.mobile.data.fundingsources.AchAccountDetails
import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.fundingsources.local.BalanceLocalDao
import com.aptopayments.mobile.repository.fundingsources.local.entities.BalanceLocalEntity
import com.aptopayments.mobile.repository.fundingsources.remote.FundingSourcesService

internal interface FundingSourceRepository : BaseNoNetworkRepository {
    fun getFundingSources(
        accountId: String,
        refresh: Boolean,
        page: Int,
        rows: Int
    ): Either<Failure, List<Balance>>

    fun assignAchAccountToBalance(balanceId: String): Either<Failure, AchAccountDetails>
    fun getAchAccountDetails(balanceId: String): Either<Failure, AchAccountDetails>
}

internal class FundingSourceRepositoryImpl(
    private val service: FundingSourcesService,
    private val balanceLocalDao: BalanceLocalDao
) : FundingSourceRepository {

    override fun getFundingSources(
        accountId: String,
        refresh: Boolean,
        page: Int,
        rows: Int
    ): Either<Failure, List<Balance>> {
        return if (refresh) {
            getFundingSourcesFromAPI(accountId = accountId, page = page, rows = rows)
        } else {
            balanceLocalDao.getBalances()?.map { it.toBalance() }?.right() ?: getFundingSourcesFromAPI(
                accountId = accountId,
                page = page,
                rows = rows
            )
        }
    }

    override fun assignAchAccountToBalance(balanceId: String): Either<Failure, AchAccountDetails> =
        service.assignAchAccountToBalance(balanceId)

    override fun getAchAccountDetails(balanceId: String): Either<Failure, AchAccountDetails> =
        service.getAchAccountDetails(balanceId)

    private fun getFundingSourcesFromAPI(accountId: String, page: Int, rows: Int) =
        service.getFundingSources(accountId, page, rows).runIfRight { balanceList ->
            balanceLocalDao.clearBalanceCache()
            balanceLocalDao.saveBalances(balanceList.map { BalanceLocalEntity.fromBalance(it) })
        }
}
