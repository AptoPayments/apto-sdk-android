package com.aptopayments.mobile.data

/**
 * @property startingAfter Fetches the elements starting after the id provided.
 * @property endingBefore Fetches the elements ending before the id provided.
 * @property limit Sets the amount of max elements per page (Optional Parameter).
 */
data class ListPagination(
    val startingAfter: String? = null,
    val endingBefore: String? = null,
    val limit: Int? = null
)
