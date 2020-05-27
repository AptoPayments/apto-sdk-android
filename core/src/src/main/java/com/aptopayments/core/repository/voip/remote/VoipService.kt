package com.aptopayments.core.repository.voip.remote

import com.aptopayments.core.network.ApiCatalog
import com.aptopayments.core.platform.BaseService
import com.aptopayments.core.repository.voip.remote.requests.GetTokensRequest

internal class VoipService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val voipApi by lazy { apiCatalog.api().create(VoipApi::class.java) }

    fun getTokens(getTokensRequest: GetTokensRequest) = voipApi.getTokens(request = getTokensRequest)
}
