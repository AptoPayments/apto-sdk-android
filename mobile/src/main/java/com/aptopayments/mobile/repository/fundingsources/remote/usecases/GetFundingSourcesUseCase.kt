package com.aptopayments.mobile.repository.fundingsources.remote.usecases

import com.aptopayments.mobile.data.fundingsources.Balance
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.fundingsources.FundingSourceRepository

internal class GetFundingSourcesUseCase constructor(
    private val fundingSourceRepository: FundingSourceRepository,
    networkHandler: NetworkHandler
) : UseCase<List<Balance>, GetFundingSourcesUseCase.Params>(networkHandler) {

    override fun run(params: Params): Either<Failure, List<Balance>> =
        fundingSourceRepository.getFundingSources(params.cardId, params.refresh, params.page, params.rows)

    data class Params(
        val cardId: String,
        val refresh: Boolean = true,
        val page: Int,
        val rows: Int
    )
}
