package com.aptopayments.mobile.repository.user.usecases

import com.aptopayments.mobile.data.user.agreements.ReviewAgreementsInput
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.user.UserRepository

internal class ReviewAgreementsUseCase(
    private val repository: UserRepository,
    networkHandler: NetworkHandler
) : UseCase<Unit, ReviewAgreementsInput>(networkHandler) {

    override fun run(params: ReviewAgreementsInput) = repository.reviewAgreements(params)
}
