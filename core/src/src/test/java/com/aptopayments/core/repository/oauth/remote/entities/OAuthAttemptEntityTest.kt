package com.aptopayments.core.repository.oauth.remote.entities

import com.aptopayments.core.data.TestDataProvider
import com.aptopayments.core.network.ApiCatalog
import org.junit.Assert.*
import org.junit.Test

class OAuthAttemptEntityTest {

    @Test
    fun `when all data in UserData has known type then all list is parsed correctly`() {
        val json = TestDataProvider.provideCorrectOAuthAttemptEntityJsonDataPoint()
        val sut = ApiCatalog.gson().fromJson(json, OAuthAttemptEntity::class.java)
        assertEquals(sut.userData?.rows, sut.getUserDataContent()?.size)
    }

    @Test
    fun `when userData contains Unknown data point then the rest is parsed correctly `() {
        val json = TestDataProvider.provideOAuthAttemptEntityJsonWithUnknownDataPoint()
        val sut = ApiCatalog.gson().fromJson(json, OAuthAttemptEntity::class.java)
        sut.toOAuthAttempt()
    }
}
