package com.aptopayments.mobile.network

import com.aptopayments.mobile.data.PaginatedList
import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class PaginatedListEntity<T>(

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("has_more")
    var hasMore: Boolean = false,

    @SerializedName("total")
    var totalCount: Int = 0,

    @SerializedName("data")
    var data: List<T>? = null

) : Serializable {
    fun <R> toPaginatedList(transform: ((T) -> R)): PaginatedList<R> {
        val list = data?.map { transform(it) } ?: emptyList()
        return PaginatedList(hasMore = hasMore, total = totalCount, data = list)
    }
}
