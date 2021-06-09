package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.UseCaseTest
import com.aptopayments.mobile.data.TestDataProvider
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.extension.shouldBeLeftAndEqualTo
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.left
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.repository.UserSessionRepository
import com.aptopayments.mobile.repository.card.local.CardBalanceLocalDao
import com.aptopayments.mobile.repository.card.local.CardLocalRepository
import com.aptopayments.mobile.repository.card.remote.CardService
import com.aptopayments.mobile.repository.card.usecases.OrderPhysicalCardUseCase.Params
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever

import org.junit.jupiter.api.Test
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.inject

internal class OrderPhysicalCardUseCaseTest : UseCaseTest() {
    private val cardId = TestDataProvider.provideCardId()
    private val card = TestDataProvider.provideCard(cardId)

    private val service: CardService = mock()

    private val sut: OrderPhysicalCardUseCase by inject()

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
    fun `when UseCase is run then correct card is provided when ordered`() {
        whenever(service.orderPhysicalCard(cardId)).thenReturn(card.right())

        val result = sut.run(Params(cardId = cardId))

        result.shouldBeRightAndEqualTo(card)
    }

    @Test
    fun `when UseCase is run then correct card is provided`() {
        val error = Failure.ServerError(1)

        whenever(service.orderPhysicalCard(cardId)).thenReturn(error.left())

        val result = sut.run(Params(cardId = cardId))

        result.shouldBeLeftAndEqualTo(error)
    }
}
