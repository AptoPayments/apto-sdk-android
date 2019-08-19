package com.aptopayments.core.repository.fundingsources.remote.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.fundingsources.Balance
import com.aptopayments.core.exception.Failure
import com.aptopayments.core.functional.Either
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.fundingsources.FundingSourceRepository
import java.lang.reflect.Modifier

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class GetFundingSourcesUseCase constructor(
        private val fundingSourceRepository: FundingSourceRepository,
        networkHandler: NetworkHandler
) : UseCase<List<Balance>, GetFundingSourcesUseCase.Params>(networkHandler) {

    override fun run(params: Params): Either<Failure, List<Balance>> =
            fundingSourceRepository.getFundingSources(params.cardId, params.refresh)

    data class Params (
            val cardId: String,
            val refresh: Boolean = true
    )

}
