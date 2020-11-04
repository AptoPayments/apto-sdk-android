package com.aptopayments.mobile.repository.voip.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseNetworkService
import com.aptopayments.mobile.repository.voip.remote.entities.VoipCallEntity
import com.aptopayments.mobile.repository.voip.remote.requests.GetTokensRequest

internal class VoipService constructor(apiCatalog: ApiCatalog) : BaseNetworkService() {

    private val voipApi by lazy { apiCatalog.api().create(VoipApi::class.java) }

    fun getTokens(getTokensRequest: GetTokensRequest) =
        request(
            voipApi.getTokens(request = getTokensRequest),
            { it.toVoipCall() },
            VoipCallEntity()
        )
}
