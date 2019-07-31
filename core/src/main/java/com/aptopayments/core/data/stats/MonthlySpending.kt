package com.aptopayments.core.data.stats

data class MonthlySpending(
        val prevSpendingExists: Boolean,
        val nextSpendingExists: Boolean,
        val spending: List<CategorySpending>
)
