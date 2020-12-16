package com.aptopayments.mobile.repository.stats.remote.entities

import com.aptopayments.mobile.data.stats.CategorySpending
import com.aptopayments.mobile.repository.card.remote.entities.MoneyEntity
import com.google.gson.annotations.SerializedName

internal data class CategorySpendingEntity(

    @SerializedName("category_id")
    val categoryId: String = "",

    @SerializedName("spending")
    val spending: MoneyEntity? = null,

    @SerializedName("difference")
    val difference: String? = null

) {
    fun toCategorySpending() = CategorySpending(
        categoryId = categoryId,
        spending = spending?.toMoney(),
        difference = difference
    )
}
