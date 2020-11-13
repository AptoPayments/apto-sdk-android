package com.aptopayments.mobile.repository.oauth.remote

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.user.DataPointList
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.repository.oauth.remote.entities.OAuthAttemptEntity
import com.aptopayments.mobile.repository.oauth.remote.entities.OAuthUserDataUpdateEntity

import org.junit.Test
import org.koin.test.inject

private const val ID = "id_1234"

internal class OAuthServiceTest : NetworkServiceTest() {

    val sut: OAuthService by inject()

    @Test
    fun `when startOAuthAuthentication then request is made to the correct url`() {
        enqueueContent("{}")

        sut.startOAuthAuthentication(TestDataProvider.provideAllowedBalanceType())

        assertRequestSentTo("v1/oauth", "POST")
    }

    @Test
    fun `when startOAuthAuthentication then correct request is sent`() {
        enqueueContent("{}")

        sut.startOAuthAuthentication(TestDataProvider.provideAllowedBalanceType())

        assertRequestBodyFile("oauthStartRequest200.json")
    }

    @Test
    fun `when startOAuthAuthentication then response parses correctly`() {
        enqueueFile("oauthStartResponse200.json")
        val fileContent = readFile("oauthStartResponse200.json")
        val entity = parseEntity(fileContent, OAuthAttemptEntity::class.java).toOAuthAttempt()
        enqueueContent(fileContent)

        val response = sut.startOAuthAuthentication(TestDataProvider.provideAllowedBalanceType())

        response.shouldBeRightAndEqualTo(entity)
    }

    @Test
    fun `when getOAuthAttemptStatus then request is made to the correct url`() {
        enqueueContent("{}")

        sut.getOAuthAttemptStatus(ID)

        assertRequestSentTo("v1/oauth/$ID", "GET")
    }

    @Test
    fun `when getOAuthAttemptStatus then response parses correctly`() {
        enqueueFile("oauthGetStatus200.json")
        val fileContent = readFile("oauthGetStatus200.json")
        val entity = parseEntity(fileContent, OAuthAttemptEntity::class.java).toOAuthAttempt()
        enqueueContent(fileContent)

        val response = sut.getOAuthAttemptStatus(ID)

        response.shouldBeRightAndEqualTo(entity)
    }

    @Test
    fun `when saveOAuthUserData then request is made to the correct url`() {
        enqueueContent("{}")

        sut.saveOAuthUserData(
            allowedBalanceType = TestDataProvider.provideAllowedBalanceType(),
            dataPointList = DataPointList(),
            tokenId = ID
        )

        assertRequestSentTo("v1/oauth/userdata/save", "POST")
    }

    @Test
    fun `when saveOAuthUserData then response parses correctly`() {
        enqueueFile("oauthSaveResponse200.json")
        val fileContent = readFile("oauthSaveResponse200.json")
        val entity = parseEntity(fileContent, OAuthUserDataUpdateEntity::class.java).toOAuthUserDataUpdate()
        enqueueContent(fileContent)

        val response = sut.saveOAuthUserData(
            allowedBalanceType = TestDataProvider.provideAllowedBalanceType(),
            dataPointList = DataPointList(),
            tokenId = ID
        )

        response.shouldBeRightAndEqualTo(entity)
    }

    @Test
    fun `when retrieveOAuthUserData then request is made to the correct url`() {
        enqueueContent("{}")

        sut.retrieveOAuthUserData(
            allowedBalanceType = TestDataProvider.provideAllowedBalanceType(),
            tokenId = ID
        )

        assertRequestSentTo("v1/oauth/userdata/retrieve", "POST")
    }

    @Test
    fun `when retrieveOAuthUserData then correct request is sent`() {
        enqueueContent("{}")

        sut.retrieveOAuthUserData(
            allowedBalanceType = TestDataProvider.provideAllowedBalanceType(),
            tokenId = ID
        )

        assertRequestBodyFile("oauthRetrieveRequest.json")
    }
}
