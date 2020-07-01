package com.aptopayments.mobile.repository.verification.remote.entities

import com.aptopayments.mobile.ServiceTest
import com.aptopayments.mobile.data.PhoneNumber
import org.junit.Assert.*
import org.junit.Test
import org.koin.test.inject
import java.net.HttpURLConnection.HTTP_OK

private const val PHONE_NUMBER = "123456"
private const val PHONE_COUNTRY = "1"
private val PHONE = PhoneNumber(PHONE_COUNTRY, PHONE_NUMBER)
private const val VERIFICATION_ID = "entity_12345"
private const val VERIFICATION = "111111"

internal class VerificationServiceTest : ServiceTest() {

    private val sut: VerificationService by inject()

    @Test
    fun `when startVerification then request is made to the correct url`() {
        enqueueFile("verificationStartResponse200.json")

        val response = sut.startVerification(PHONE).execute()

        assertRequestSentTo("v1/verifications/start", "POST")
        assertCode(response, HTTP_OK)
    }

    @Test
    fun `when startVerification then response parses correctly`() {
        val fileContent = readFile("verificationStartResponse200.json")
        val entity = parseEntity(fileContent, VerificationEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.startVerification(PHONE).execute()

        assertEquals(entity, response.body()!!)
        assertNotNull(entity.toVerification())
    }

    @Test
    fun `when vefificationFinish then request is made to the correct url`() {
        enqueueFile("verificationFinishResponse200.json")

        val response = sut.finishVerification(VERIFICATION_ID, VERIFICATION).execute()

        assertRequestSentTo("v1/verifications/$VERIFICATION_ID/finish", "POST")
        assertCode(response, HTTP_OK)
    }

    @Test
    fun `when vefificationFinish then response parses correctly`() {
        val fileContent = readFile("verificationFinishResponse200.json")
        val entity = parseEntity(fileContent, VerificationEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.finishVerification(VERIFICATION_ID, VERIFICATION).execute()

        assertEquals(entity, response.body()!!)
        assertNotNull(entity.toVerification())
    }
}
