package com.aptopayments.core.repository.stats.remote.entities

import com.aptopayments.core.data.stats.CategorySpending
import com.aptopayments.core.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName

internal data class CategorySpendingEntity(

    @SerializedName("category_id")
    var categoryId: String = "",

    @SerializedName("spending")
    var spending: MoneyEntity? = null,

    @SerializedName("difference")
    var difference: String? = null

) {
    fun toCategorySpending() = CategorySpending(
        categoryId = categoryId,
        spending = spending?.toMoney(),
        difference = difference
    )
}
