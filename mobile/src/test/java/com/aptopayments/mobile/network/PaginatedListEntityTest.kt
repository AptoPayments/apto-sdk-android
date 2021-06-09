package com.aptopayments.mobile.network

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

private const val HAS_MORE = true
private const val TOTAL_COUNT = 9
private val list = listOf(1, 2, 3)

internal class PaginatedListEntityTest {

    val sut = PaginatedListEntity(type = "test", hasMore = HAS_MORE, totalCount = TOTAL_COUNT, list)

    @Test
    fun `toPaginatedList convert totalCount correctly`() {
        val newList = sut.toPaginatedList { }

        assertEquals(TOTAL_COUNT, newList.total)
    }

    @Test
    fun `toPaginatedList maps correctly when has elements`() {
        val transformation = { number: Int -> number + 1 }
        val mappedList = list.map(transformation)

        val newList = sut.toPaginatedList(transformation)

        assertEquals(list.size, newList.data.size)
        assertEquals(mappedList, newList.data)
    }

    @Test
    fun `toPaginatedList data has empty list when listEntity has null data`() {
        val transformation = { number: Int -> number + 1 }
        val sut = PaginatedListEntity<Int>(data = null)

        val newList = sut.toPaginatedList(transformation)

        assertEquals(emptyList<Int>(), newList.data)
    }

    @Test
    fun `whenever hasMore is false then paginatedList has no more pages`() {
        val sut = PaginatedListEntity<Int>(hasMore = false)

        val newList = sut.toPaginatedList { }

        assertFalse(newList.hasMore)
    }

    @Test
    fun `whenever hasMore is true then paginatedList has more pages`() {
        val sut = PaginatedListEntity<Int>(hasMore = true)

        val newList = sut.toPaginatedList { }

        assertTrue(newList.hasMore)
    }
}
