package com.aptopayments.mobile.network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

internal data class ListEntity<T>(

    @SerializedName("type")
    var type: String? = null,

    @SerializedName("page")
    var page: Int = 0,

    @SerializedName("rows")
    var rows: Int = 0,

    @SerializedName("has_more")
    var hasMore: Boolean = false,

    @SerializedName("total_count")
    var totalCount: Int = 0,

    @SerializedName("data")
    var data: List<T>? = null

) : Serializable
