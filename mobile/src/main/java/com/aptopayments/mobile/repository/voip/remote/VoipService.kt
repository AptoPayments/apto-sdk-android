package com.aptopayments.mobile.repository.voip.remote

import com.aptopayments.mobile.network.ApiCatalog
import com.aptopayments.mobile.platform.BaseService
import com.aptopayments.mobile.repository.voip.remote.requests.GetTokensRequest

internal class VoipService constructor(apiCatalog: ApiCatalog) : BaseService() {

    private val voipApi by lazy { apiCatalog.api().create(VoipApi::class.java) }

    fun getTokens(getTokensRequest: GetTokensRequest) = voipApi.getTokens(request = getTokensRequest)
}
