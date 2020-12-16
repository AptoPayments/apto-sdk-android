package com.aptopayments.mobile.data

import java.io.Serializable

data class PaginatedList<T>(
    val hasMore: Boolean = false,
    val total: Int = 0,
    val data: List<T> = emptyList()
) : Serializable
