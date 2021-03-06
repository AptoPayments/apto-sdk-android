package com.aptopayments.mobile.data.stats

import com.aptopayments.mobile.data.card.Money

/**
 * Holds the data for an MCC grouped spending in a certain month
 *
 * @property categoryId an MCC Name
 * @property spending the amount spent in a certain category in a certain period
 * @property difference
 */
data class CategorySpending(
    val categoryId: String,
    val spending: Money?,
    val difference: String? = null
)
