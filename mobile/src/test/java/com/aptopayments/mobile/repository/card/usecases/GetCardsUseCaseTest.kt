package com.aptopayments.mobile.repository.card.usecases

import com.aptopayments.mobile.data.ListPagination
import com.aptopayments.mobile.data.PaginatedList
import com.aptopayments.mobile.data.card.Card
import com.aptopayments.mobile.exception.Failure
import com.aptopayments.mobile.extension.shouldBeRightAndEqualTo
import com.aptopayments.mobile.functional.left
import com.aptopayments.mobile.functional.right
import com.aptopayments.mobile.network.NetworkHandler
import com.aptopayments.mobile.repository.card.CardRepository
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class GetCardsUseCaseTest {
    val repository: CardRepository = mock()
    val networkHandler: NetworkHandler = mock()

    val sut = GetCardsUseCase(repository, networkHandler)

    @Test
    fun `whenever repository fails then useCase fails`() {
        val failure = Failure.ServerError(null).left()
        whenever(repository.getCards(anyOrNull())).thenReturn(failure)

        val result = sut.run(ListPagination())

        assertEquals(failure, result)
    }

    @Test
    fun `whenever repository success then useCase success with same data`() {
        val list: PaginatedList<Card> = mock()
        whenever(repository.getCards(anyOrNull())).thenReturn(list.right())

        val result = sut.run(ListPagination())

        result.shouldBeRightAndEqualTo(list)
    }
}
