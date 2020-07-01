package com.aptopayments.mobile.repository.oauth.remote.entities

import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.network.GsonProvider
import org.junit.Assert.*
import org.junit.Test

class OAuthAttemptEntityTest {

    @Test
    fun `when all data in UserData has known type then all list is parsed correctly`() {
        val json = TestDataProvider.provideCorrectOAuthAttemptEntityJsonDataPoint()
        val sut = GsonProvider.provide().fromJson(json, OAuthAttemptEntity::class.java)
        assertEquals(sut.userData?.rows, sut.getUserDataContent()?.size)
    }

    @Test
    fun `when userData contains Unknown data point then the rest is parsed correctly `() {
        val json = TestDataProvider.provideOAuthAttemptEntityJsonWithUnknownDataPoint()
        val sut = GsonProvider.provide().fromJson(json, OAuthAttemptEntity::class.java)
        sut.toOAuthAttempt()
    }
}
