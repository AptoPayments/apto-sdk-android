package com.aptopayments.mobile.repository.fundingsources

import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.platform.BaseNoNetworkRepository
import com.aptopayments.mobile.repository.fundingsources.local.BalanceLocalDao
import com.aptopayments.mobile.repository.fundingsources.local.entities.BalanceLocalEntity
import com.aptopayments.mobile.repository.fundingsources.remote.FundingSourcesService

internal interface FundingSourceRepository : BaseNoNetworkRepository {
    fun getFundingSources(accountId: String, refresh: Boolean, page: Int, rows: Int): Either<Failure, List<Balance>>
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
            getFromAPI(accountId = accountId, page = page, rows = rows)
        } else {
            balanceLocalDao.getBalances()?.map { it.toBalance() }?.right() ?: getFromAPI(
                accountId = accountId,
                page = page,
                rows = rows
            )
        }
    }

    private fun getFromAPI(accountId: String, page: Int, rows: Int) =
        service.getFundingSources(accountId, page, rows).runIfRight { balanceList ->
            balanceLocalDao.clearBalanceCache()
            balanceLocalDao.saveBalances(balanceList.map { BalanceLocalEntity.fromBalance(it) })
        }
}
