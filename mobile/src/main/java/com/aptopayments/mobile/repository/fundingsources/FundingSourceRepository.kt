package com.aptopayments.mobile.repository.fundingsources

import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.network.ListEntity
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.platform.BaseRepository
import com.aptopayments.mobile.repository.fundingsources.local.BalanceLocalDao
import com.aptopayments.mobile.repository.fundingsources.local.entities.BalanceLocalEntity
import com.aptopayments.mobile.repository.fundingsources.remote.FundingSourcesService
import com.aptopayments.mobile.repository.fundingsources.remote.entities.BalanceEntity

internal interface FundingSourceRepository : BaseRepository {

    fun getFundingSources(accountId: String, refresh: Boolean, page: Int, rows: Int): Either<Failure, List<Balance>>

    class Network constructor(
        private val networkHandler: NetworkHandler,
        private val service: FundingSourcesService,
        private val balanceLocalDao: BalanceLocalDao
    ) : BaseRepository.BaseRepositoryImpl(), FundingSourceRepository {

        override fun getFundingSources(
            accountId: String,
            refresh: Boolean,
            page: Int,
            rows: Int
        ): Either<Failure, List<Balance>> {
            if (refresh) {
                return getFundingSourcesFromRemoteAPI(accountId = accountId, page = page, rows = rows)
            } else {
                balanceLocalDao.getBalances()?.let { localBalances ->
                    return Either.Right(localBalances.map { it.toBalance() })
                } ?: return getFundingSourcesFromRemoteAPI(accountId = accountId, page = page, rows = rows)
            }
        }

        private fun getFundingSourcesFromRemoteAPI(
            accountId: String,
            page: Int,
            rows: Int
        ): Either<Failure, List<Balance>> {
            return when (networkHandler.isConnected) {
                true -> {
                    request(service.getFundingSources(accountId, page, rows), { listEntity: ListEntity<BalanceEntity> ->
                        val balanceList = listEntity.data?.map {
                            it.toBalance()
                        } ?: emptyList()
                        balanceLocalDao.clearBalanceCache()
                        balanceLocalDao.saveBalances(balanceList.map { BalanceLocalEntity.fromBalance(it) })
                        balanceList
                    }, ListEntity())
                }
                false -> Either.Left(Failure.NetworkConnection)
            }
        }
    }
}
