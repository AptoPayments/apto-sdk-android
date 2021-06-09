package com.aptopayments.mobile.repository.verification.remote.entities

import com.aptopayments.mobile.NetworkServiceTest
import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.koin.test.inject

private const val PHONE_NUMBER = "123456"
private const val PHONE_COUNTRY = "1"
private val PHONE = PhoneNumber(PHONE_COUNTRY, PHONE_NUMBER)
private const val VERIFICATION_ID = "entity_12345"
private const val VERIFICATION = "111111"

internal class VerificationServiceTest : NetworkServiceTest() {

    private val sut: VerificationService by inject()

    @Test
    fun `when startVerification then request is made to the correct url`() {
        enqueueFile("verificationStartResponse200.json")

        sut.startVerification(PHONE)

        assertRequestSentTo("v1/verifications/start", "POST")
    }

    @Test
    fun `when startVerification then response parses correctly`() {
        val fileContent = readFile("verificationStartResponse200.json")
        val entity = parseEntity(fileContent, VerificationEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.startVerification(PHONE)

        response.shouldBeRightAndEqualTo(entity.toVerification())
        assertNotNull(entity.toVerification())
    }

    @Test
    fun `when verificationFinish then request is made to the correct url`() {
        enqueueFile("verificationFinishResponse200.json")

        sut.finishVerification(VERIFICATION_ID, VERIFICATION)

        assertRequestSentTo("v1/verifications/$VERIFICATION_ID/finish", "POST")
    }

    @Test
    fun `when verificationFinish then response parses correctly`() {
        val fileContent = readFile("verificationFinishResponse200.json")
        val entity = parseEntity(fileContent, VerificationEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.finishVerification(VERIFICATION_ID, VERIFICATION)

        response.shouldBeRightAndEqualTo(entity.toVerification())
        assertNotNull(entity.toVerification())
    }

    @Test
    fun `when startPrimaryVerification then request is made to the correct url`() {
        enqueueContent("{}")

        sut.startPrimaryVerification()

        assertRequestSentTo("v1/verifications/primary/start", "POST")
    }

    @Test
    fun `when startPrimaryVerification then response parses correctly`() {
        val fileContent = readFile("verificationStartResponse200.json")
        val entity = parseEntity(fileContent, VerificationEntity::class.java)
        enqueueContent(fileContent)

        val response = sut.startPrimaryVerification()

        response.shouldBeRightAndEqualTo(entity.toVerification())
        assertNotNull(entity.toVerification())
    }
}
