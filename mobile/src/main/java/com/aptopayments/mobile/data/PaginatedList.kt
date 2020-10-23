package com.aptopayments.mobile.data

import java.io.Serializable

data class PaginatedList<T>(
    var hasMore: Boolean = false,
    var total: Int = 0,
    var data: List<T> = emptyList()
) : Serializable
