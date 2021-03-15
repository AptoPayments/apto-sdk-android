package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.UseCaseTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.card.local.CardBalanceLocalDao
import com.aptopayments.mobile.repository.card.local.CardLocalRepository
import com.aptopayments.mobile.repository.card.remote.CardService
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.inject

internal class UnlockCardUseCaseTest : UseCaseTest() {
    private val cardId = TestDataProvider.provideCardId()
    private val card = TestDataProvider.provideCard(cardId)

    private val service: CardService = mock()

    private val sut: UnlockCardUseCase by inject()

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
        whenever(service.unlockCard(cardId)).thenReturn(card.right())

        val result = sut.run(cardId)

        result.shouldBeRightAndEqualTo(card)
    }
}
