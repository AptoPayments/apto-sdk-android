package com.aptopayments.core.data.stats

import com.aptopayments.core.data.card.Money

data class CategorySpending(
    var categoryId: String,
    val spending: Money?,
    val difference: String? = null
)
