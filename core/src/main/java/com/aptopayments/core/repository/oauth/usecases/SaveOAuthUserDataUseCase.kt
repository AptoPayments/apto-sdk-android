package com.aptopayments.core.repository.oauth.usecases

import androidx.annotation.VisibleForTesting
import com.aptopayments.core.data.oauth.OAuthUserDataUpdate
import com.aptopayments.core.data.user.DataPointList
import com.aptopayments.core.data.workflowaction.AllowedBalanceType
import com.aptopayments.core.interactor.UseCase
import com.aptopayments.core.network.NetworkHandler
import com.aptopayments.core.repository.oauth.OAuthRepository
import java.lang.reflect.Modifier
import javax.inject.Inject

@VisibleForTesting(otherwise = Modifier.PROTECTED)
internal class SaveOAuthUserDataUseCase @Inject constructor(
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
            tokenId = params.tokenId)
}
