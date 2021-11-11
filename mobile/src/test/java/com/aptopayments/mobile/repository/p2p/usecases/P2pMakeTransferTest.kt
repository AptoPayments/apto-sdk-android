package com.aptopayments.mobile.repository.p2p.usecases

import com.aptopayments.mobile.UseCaseTest
import com.aptopayments.mobile.data.card.Money
import com.aptopayments.mobile.data.payment.PaymentStatus
import com.aptopayments.mobile.data.transfermoney.CardHolderName
import com.aptopayments.mobile.data.transfermoney.P2pTransferResponse
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.p2p.remote.P2pService
import org.junit.jupiter.api.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.inject
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.threeten.bp.ZonedDateTime

internal class P2pMakeTransferTest : UseCaseTest() {

    private val sourceId = "so_123"
    private val recipientId = "res_1234"
    private val amount = Money("USD", 123.0)
    private val response =
        P2pTransferResponse("id", PaymentStatus.PROCESSED, sourceId, amount, CardHolderName("john", "doe"), ZonedDateTime.now())

    private val service: P2pService = mock()

    private val sut: P2pMakeTransfer by inject()

    override fun setUpKoin() {
        loadKoinModules(
            module {
                single(override = true) { service }
            }
        )
    }

    @Test
    internal fun `given source recipient and amount when UseCase is called then service response is returned`() {
        whenever(
            service.makeTransfer(sourceId = sourceId, recipientId = recipientId, amount = amount)
        ).thenReturn(response.right())

        val params = P2pMakeTransfer.Params(sourceId = sourceId, recipientId = recipientId, amount = amount)

        val result = sut.run(params)

        result.shouldBeRightAndEqualTo(response)
    }
}
