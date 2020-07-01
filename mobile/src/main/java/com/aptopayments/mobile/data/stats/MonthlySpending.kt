package com.aptopayments.mobile.data.stats

/**
 * MonthlySpending
 *
 * @property prevSpendingExists True if there is a previous MonthlySpending, this depends on the current month and
 * the activation date of the card
 * @property nextSpendingExists False if this data is from the current month, True otherwise
 * @property spending List<CategorySpending> containing the category spending of the month
 */
data class MonthlySpending(
    val prevSpendingExists: Boolean,
    val nextSpendingExists: Boolean,
    val spending: List<CategorySpending>
)
