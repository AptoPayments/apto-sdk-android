package com.aptopayments.mobile.repository.oauth.usecases

import com.aptopayments.mobile.data.oauth.OAuthUserDataUpdate
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.data.workflowaction.AllowedBalanceType
import com.aptopayments.mobile.interactor.UseCase
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.oauth.OAuthRepository

internal class SaveOAuthUserDataUseCase(
    private val repository: OAuthRepository,
    networkHandler: NetworkHandler
) : UseCase<OAuthUserDataUpdate, SaveOAuthUserDataUseCase.Params>(networkHandler) {

    data class Params(
        val allowedBalanceType: AllowedBalanceType,
        val dataPointList: DataPointList,
        val tokenId: String
    )

    override fun run(params: Params) = repository.saveOAuthUserData(
        allowedBalanceType = params.allowedBalanceType,
        dataPointList = params.dataPointList,
        tokenId = params.tokenId
    )
}
