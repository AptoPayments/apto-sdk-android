package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.UseCaseTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.data.card.ActivatePhysicalCardResult
import com.aptopayments.mobile.data.card.ActivatePhysicalCardResultType
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.card.local.CardBalanceLocalDao
import com.aptopayments.mobile.repository.card.local.CardLocalRepository
import com.aptopayments.mobile.repository.card.remote.CardService
import com.aptopayments.mobile.repository.card.usecases.ActivatePhysicalCardUseCase.Params
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.junit.jupiter.api.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.inject

private const val ACTIVATE_CODE = "123456"

internal class ActivatePhysicalCardUseCaseTest : UseCaseTest() {
    private val cardId = TestDataProvider.provideCardId()
    private val card = TestDataProvider.provideCard(cardId)

    private val service: CardService = mock()

    private val sut: ActivatePhysicalCardUseCase by inject()

    override fun setUpKoin() {
        loadKoinModules(
            module {
                single(override = true) { mock<UserSessionRepository>() }
                single(override = true) { mock<CardLocalRepository>() }
                single(override = true) { mock<CardBalanceLocalDao>() }
                single(override = true) { service }
            }
        )
    }

    @Test
    fun `when UseCase is run then correct card is provided`() {
        val activateResult = ActivatePhysicalCardResult(ActivatePhysicalCardResultType.ACTIVATED)
        whenever(service.activatePhysicalCard(cardId, ACTIVATE_CODE)).thenReturn(activateResult.right())

        val result = sut.run(Params(cardId, ACTIVATE_CODE))

        result.shouldBeRightAndEqualTo(activateResult)
    }
}
