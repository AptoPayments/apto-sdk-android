package com.aptopayments.mobile.repository.p2p.usecases

import com.aptopayments.mobile.UseCaseTest
import com.aptopayments.mobile.data.PhoneNumber
import com.aptopayments.mobile.data.transfermoney.CardHolderData
import com.aptopayments.mobile.data.transfermoney.CardHolderName
import com.aptopayments.mobile.exception.NoEnoughParametersFailure
import com.aptopayments.mobile.exception.TooMuchParametersFailure
import com.aptopayments.mobile.extension.shouldBeLeftAndInstanceOf
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.p2p.remote.P2pService
import com.aptopayments.mobile.repository.p2p.usecases.P2pFindRecipientUseCase.Params
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.inject
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

internal class P2pFindRecipientUseCaseTest : UseCaseTest() {

    private val recipient =
        CardHolderData(CardHolderName(firstName = "Hello", lastName = "World"), "crd_1234")
    private val phone = PhoneNumber("1", "12345")
    private val email = "hello@aptopayments.com"

    private val service: P2pService = mock()

    private val sut: P2pFindRecipientUseCase by inject()

    override fun setUpKoin() {
        loadKoinModules(
            module {
                single(override = true) { service }
            }
        )
    }

    @BeforeEach
    override fun setUp() {
        super.setUp()
    }

    @Test
    internal fun `given no parameters when UseCase is called then Left is returned`() {
        val params = Params()

        val result = sut.run(params)

        result.shouldBeLeftAndInstanceOf(NoEnoughParametersFailure::class.java)
    }

    @Test
    internal fun `given both parameters when UseCase is called then Left is returned`() {
        val params = Params(phone = phone, email = email)

        val result = sut.run(params)

        result.shouldBeLeftAndInstanceOf(TooMuchParametersFailure::class.java)
    }

    @Test
    internal fun `given phone when UseCase is called then recipient is returned`() {
        whenever(service.findRecipient(phone)).thenReturn(recipient.right())
        val params = Params(phone = phone)

        val result = sut.run(params)

        result.shouldBeRightAndEqualTo(recipient)
    }

    @Test
    internal fun `given email when UseCase is called then recipient is returned`() {
        whenever(service.findRecipient(email = email)).thenReturn(recipient.right())
        val params = Params(email = email)

        val result = sut.run(params)

        result.shouldBeRightAndEqualTo(recipient)
    }
}
