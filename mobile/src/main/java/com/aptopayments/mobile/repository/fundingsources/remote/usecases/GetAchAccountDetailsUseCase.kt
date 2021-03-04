package com.aptopayments.mobile.repository.fundingsources.remote.usecases

import com.aptopayments.mobile.data.fundingsources.AchAccountDetails
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.functional.Either
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.fundingsources.FundingSourceRepository

internal class GetAchAccountDetailsUseCase(
    private val repo: FundingSourceRepository,
    networkHandler: NetworkHandler
) : UseCase<AchAccountDetails, String>(networkHandler) {

    override fun run(params: String): Either<Failure, AchAccountDetails> =
        repo.getAchAccountDetails(params)
}
