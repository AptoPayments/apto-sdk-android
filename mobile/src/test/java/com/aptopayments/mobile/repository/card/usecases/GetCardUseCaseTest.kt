package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.UseCaseTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.card.local.CardBalanceLocalDao
import com.aptopayments.mobile.repository.card.local.CardLocalRepository
import com.aptopayments.mobile.repository.card.remote.CardService
import org.mockito.kotlin.*

import org.junit.jupiter.api.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.inject

internal class GetCardUseCaseTest : UseCaseTest() {
    private val cardId = TestDataProvider.provideCardId()
    val card = TestDataProvider.provideCard(accountID = cardId)
    private val service: CardService = mock()
    private val cardLocalRepo: CardLocalRepository = mock()

    private val userCase: GetCardUseCase by inject()

    override fun setUpKoin() {
        loadKoinModules(
            module {
                single(override = true) { cardLocalRepo }
                single(override = true) { mock<CardBalanceLocalDao>() }
                single(override = true) { mock<UserSessionRepository>() }
                single(override = true) { service }
            }
        )
    }

    @Test
    fun `given CardLocalRepo has data and refresh is false when runUseCase then service doesn't get called`() {
        whenever(cardLocalRepo.getCard(cardId)).thenReturn(card)

        val result = userCase.run(GetCardParams(cardId = cardId, refresh = false))

        result.shouldBeRightAndEqualTo(card)
    }

    @Test
    fun `given refresh is true when runUseCase then service gets called`() {
        whenever(service.getCard(any())).thenReturn(card.right())

        val result = userCase.run(GetCardParams(cardId = cardId, refresh = true))

        result.shouldBeRightAndEqualTo(card)
    }
}
